Fixing CORS errors when developing HabitOS

Summary
- Problem: Frontend (Vite) was calling a remote backend at http://34.158.37.131:8080/api which does not return CORS headers for requests from http://localhost:5173. Browser blocked requests during preflight (OPTIONS) or actual request.
- Fixes applied:
  1) API Gateway (Spring Cloud Gateway): allowed OPTIONS through Security so preflight requests are not blocked by Spring Security.
     - File changed: api-gateway/src/main/java/com/habitos/api_gateway/config/SecurityConfig.java
     - Change: added `.pathMatchers(HttpMethod.OPTIONS).permitAll()` before other matchers.
  2) Frontend default API URL changed to local gateway for development to avoid calling remote server that lacks CORS.
     - File changed: HabitOsFE/src/shared/services/api.js
     - Default now: http://localhost:8080/api (overridable with VITE_API_URL)
  3) Frontend .env updated for local development:
     - File changed: HabitOsFE/.env -> VITE_API_URL=http://localhost:8080/api

Why this caused the error
- CORS is enforced by browsers. If the server that handles the request (the gateway or service) doesn't include Access-Control-Allow-Origin and related headers on the response to the preflight (OPTIONS) or actual request, the browser blocks the response.
- In the screenshot, the frontend was sending requests to the remote IP which didn't include CORS headers for requests from localhost:5173.
- Also, if a security filter (Spring Security) rejects the OPTIONS preflight before a CORS filter can add the headers, the browser will still see it as blocked. That's why we permit OPTIONS in SecurityConfig.

How to test locally
1) Start server-registry, api-gateway and backend services as usual (docker-compose or mvn spring-boot:run). Ensure gateway runs on port 8080 and frontend on 5173.
2) From the machine running the frontend, test a preflight with curl (PowerShell syntax):

   curl -i -X OPTIONS "http://34.158.37.131:8080/api/habits" -H "Origin: https://habitosfedeploy.vercel.app" -H "Access-Control-Request-Method: POST"

Expected: response includes headers like:
    Access-Control-Allow-Origin: http://localhost:5173
    Access-Control-Allow-Methods: GET,POST,PUT,DELETE,OPTIONS
    Access-Control-Allow-Headers: *

3) Open the frontend app and perform the request (create habit). The browser console should no longer show CORS errors.

Notes and next steps
- In production, set VITE_API_URL to the actual gateway URL that supports CORS or configure the production gateway to emit correct CORS headers.
- For deployed frontend, set `CORS_ALLOWED_ORIGINS` in the API gateway environment to your frontend URL (comma-separated if multiple). Example:
   - `CORS_ALLOWED_ORIGINS=https://habitosfedeploy.vercel.app`
   - If you also want local dev: `CORS_ALLOWED_ORIGINS=http://localhost:5173,https://habitosfedeploy.vercel.app`
- If you still see CORS errors after these changes, check whether frontend is calling the gateway directly or some other IP (search network tab). Also verify Docker/Nginx or a cloud load balancer isn't stripping CORS headers.
- Optional: replace the CorsWebFilter.allowedOrigins with config-driven list or use `config.addAllowedOriginPattern("*")` if you must allow dynamic origins (but avoid wildcard with credentials enabled).

Contact
- If you want, I can also add integration tests or a small script to check CORS headers automatically during local startup.
