package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ═══════════════════════════════════════════════════════════════════════════════
 * CORS CONFIGURATION - Allows Frontend to Talk to Backend
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * WHAT THIS FILE DOES:
 * Allows your React frontend (running on a different URL) to call this backend API
 * Without this, browsers will block your API calls for security reasons
 *
 * REAL-WORLD EXAMPLE:
 * Imagine you're at a bank:
 * - You (Frontend) want to access your account
 * - Security guard (Browser) asks: "Are you allowed to access this branch?"
 * - Bank manager (This CORS Config) says: "Yes, this person is allowed!"
 * - Security guard lets you in
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 */

// ┌─────────────────────────────────────────────────────────────────────────┐
// │ @Configuration - Tells Spring This Is A Configuration Class             │
// └─────────────────────────────────────────────────────────────────────────┘
//
// WHAT IT DOES:
// Marks this class as a "configuration" class
// Spring will read this and apply the settings when application starts
//
// SPRING BOOT MAGIC:
// 1. Spring finds this class during component scanning
// 2. Spring creates a bean from this class
// 3. Spring calls the addCorsMappings() method
// 4. CORS settings are applied to all API endpoints
//
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    // ┌─────────────────────────────────────────────────────────────────────┐
    // │ METHOD: addCorsMappings() - Configure CORS Settings                 │
    // └─────────────────────────────────────────────────────────────────────┘
    //
    // WHAT IS CORS?
    // CORS = Cross-Origin Resource Sharing
    // It's a security feature in browsers
    //
    // THE PROBLEM WITHOUT CORS:
    // - Your frontend runs on: http://localhost:3000
    // - Your backend runs on: http://localhost:8080
    // - Browser says: "These are DIFFERENT origins!"
    // - Browser BLOCKS the API call for security
    //
    // THE SOLUTION WITH CORS:
    // - Backend says: "I allow requests from http://localhost:3000"
    // - Browser sees this permission and allows the API call
    //
    // VISUAL EXAMPLE:
    //
    // ❌ WITHOUT CORS:
    // Frontend (localhost:3000) → API Call → Backend (localhost:8080)
    //                               ↓
    //                          ❌ BLOCKED!
    //                          "CORS Error"
    //
    // ✅ WITH CORS:
    // Frontend (localhost:3000) → API Call → Backend (localhost:8080)
    //                               ↓
    //                          ✅ ALLOWED!
    //                          Backend sends: "Access-Control-Allow-Origin: *"
    //
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        // STEP 1: Apply CORS to all endpoints
        // "/**" means: All URLs in this backend
        // Examples: /api/hello, /api/students, /api/courses, etc.
        registry.addMapping("/**")

                // STEP 2: Allow requests from ANY domain
                // "*" means: Any website can call our API
                // Examples:
                // - http://localhost:3000 (your React dev server) ✅
                // - https://myapp.vercel.app (your deployed frontend) ✅
                // - https://any-website.com ✅
                .allowedOrigins("*")

                // STEP 3: Allow ALL HTTP methods
                // GET, POST, PUT, DELETE, PATCH, OPTIONS
                .allowedMethods("*")

                // STEP 4: Allow ALL headers
                // Headers like: Content-Type, Authorization, etc.
                .allowedHeaders("*");

        // THAT'S IT!
        // Now any frontend can call our API without CORS errors
    }

    // ═══════════════════════════════════════════════════════════════════════
    // IMPORTANT NOTES
    // ═══════════════════════════════════════════════════════════════════════
    //
    // 🔓 CURRENT SETTING: Allow EVERYONE (allowedOrigins("*"))
    // This is GOOD for:
    // - Learning and development
    // - Testing from different tools (Postman, browser, etc.)
    // - Prototypes and demos
    //
    // 🔒 PRODUCTION SETTING: Allow SPECIFIC domains only
    // For real applications, change to:
    // .allowedOrigins(
    //     "http://localhost:3000",                    // Development
    //     "https://myapp.vercel.app",                 // Production frontend
    //     "https://staging.myapp.vercel.app"          // Staging frontend
    // )
    //
    // WHY?
    // Security! You don't want random websites calling your API
    //
    // ═══════════════════════════════════════════════════════════════════════
}

/**
 * ═══════════════════════════════════════════════════════════════════════════════
 * COMMON QUESTIONS & ANSWERS
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * ❓ Q1: What happens if I delete this file?
 * ✅ A1: Your frontend will get CORS errors when calling the API:
 *       "Access to fetch at 'http://localhost:8080/api/hello' has been blocked by CORS policy"
 *       Browser will block the requests for security reasons
 *
 * ❓ Q2: Why do I need CORS? Isn't it annoying?
 * ✅ A2: CORS is a security feature! Without it, ANY website could:
 *       - Steal your data
 *       - Make requests on your behalf
 *       - Access private information
 *       CORS is your friend - it protects users!
 *
 * ❓ Q3: When do I get CORS errors?
 * ✅ A3: When frontend and backend are on different:
 *       - Domains: example.com vs api.example.com
 *       - Ports: localhost:3000 vs localhost:8080
 *       - Protocols: http:// vs https://
 *       This is called "Cross-Origin" request
 *
 * ❓ Q4: What does "/**" mean in addMapping?
 * ✅ A4: It's a wildcard pattern:
 *       /** = Match ALL paths
 *       /api/** = Match all paths starting with /api
 *       /api/students/** = Match all paths starting with /api/students
 *
 * ❓ Q5: Can I allow only specific HTTP methods?
 * ✅ A5: Yes! Instead of allowedMethods("*"), do:
 *       .allowedMethods("GET", "POST")
 *       This allows only GET and POST, blocks PUT and DELETE
 *
 * ❓ Q6: What are "preflight requests"?
 * ✅ A6: Before sending actual request, browser sends an OPTIONS request
 *       to check if CORS is allowed. This is called "preflight"
 *       Spring handles this automatically for you!
 *
 * ❓ Q7: How do I know if CORS is working?
 * ✅ A7: Check browser DevTools → Network tab:
 *       Look for response header: Access-Control-Allow-Origin: *
 *       If you see this header, CORS is working!
 *
 * ❓ Q8: Is allowedOrigins("*") safe?
 * ✅ A8: For learning: YES
 *       For production: NO - specify exact domains
 *       Why? You don't want random sites accessing your API
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * HOW CORS WORKS - DETAILED FLOW
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * STEP 1: Frontend makes API call
 * ──────────────────────────────
 * Code: fetch('http://localhost:8080/api/hello')
 * Browser: "Wait! This is a different origin!"
 *
 * STEP 2: Browser sends preflight request (OPTIONS)
 * ──────────────────────────────────────────────────
 * Browser → Backend: "OPTIONS /api/hello"
 * Browser asks: "Am I allowed to make this request?"
 *
 * STEP 3: Backend responds with CORS headers
 * ───────────────────────────────────────────
 * Backend → Browser:
 *   Access-Control-Allow-Origin: *
 *   Access-Control-Allow-Methods: GET, POST, PUT, DELETE
 *   Access-Control-Allow-Headers: *
 *
 * STEP 4: Browser checks headers
 * ───────────────────────────────
 * Browser: "OK, backend allows this origin!"
 * Browser: "OK, backend allows GET method!"
 * Browser: "All good, I'll allow this request"
 *
 * STEP 5: Actual request sent
 * ────────────────────────────
 * Browser → Backend: "GET /api/hello"
 * Backend → Browser: {"message":"Hello World"}
 * Frontend receives data successfully!
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * COMMON CORS ERRORS AND SOLUTIONS
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * ❌ ERROR: "Access to fetch has been blocked by CORS policy"
 * ✅ FIX: Add or check this CorsConfig.java file
 *
 * ❌ ERROR: "No 'Access-Control-Allow-Origin' header is present"
 * ✅ FIX: Make sure this file exists and @Configuration annotation is present
 *
 * ❌ ERROR: "Method DELETE is not allowed by Access-Control-Allow-Methods"
 * ✅ FIX: Change to allowedMethods("*") or add "DELETE" to the list
 *
 * ❌ ERROR: CORS works in Postman but not in browser
 * ✅ FIX: Postman doesn't enforce CORS (browsers do!)
 *        This is normal - add CORS configuration for browsers
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * TESTING CORS
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * METHOD 1: Browser DevTools
 * ──────────────────────────
 * 1. Open frontend: http://localhost:3000
 * 2. Open DevTools (F12)
 * 3. Go to Network tab
 * 4. Make API call
 * 5. Click on the request
 * 6. Look at Response Headers:
 *    - Access-Control-Allow-Origin: * ✅
 *
 * METHOD 2: Check Console
 * ────────────────────────
 * 1. Open DevTools Console
 * 2. If you see CORS error → CORS not working ❌
 * 3. If no error → CORS working ✅
 *
 * METHOD 3: Test with curl
 * ─────────────────────────
 * curl -H "Origin: http://localhost:3000" -v http://localhost:8080/api/hello
 * Look for: Access-Control-Allow-Origin header in response
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * PRODUCTION SECURITY EXAMPLE
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * For production, use specific origins:
 *
 * @Override
 * public void addCorsMappings(CorsRegistry registry) {
 *     registry.addMapping("/api/**")  // Only /api/* endpoints
 *             .allowedOrigins(
 *                 "http://localhost:3000",              // Local dev
 *                 "https://myapp.vercel.app",           // Production
 *                 "https://staging.myapp.vercel.app"    // Staging
 *             )
 *             .allowedMethods("GET", "POST", "PUT", "DELETE")  // Specific methods
 *             .allowedHeaders("Content-Type", "Authorization") // Specific headers
 *             .allowCredentials(true);                         // Allow cookies
 * }
 *
 * This is MORE SECURE because:
 * - Only your frontends can access API (not random websites)
 * - Only specific HTTP methods allowed
 * - Only specific headers allowed
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * KEY CONCEPTS TO REMEMBER
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * 1. CORS = Security feature to protect users from malicious websites
 * 2. Different origins = Different domain, port, or protocol
 * 3. Browser enforces CORS (not the server)
 * 4. Backend must explicitly allow cross-origin requests
 * 5. allowedOrigins("*") = OK for learning, NOT for production
 * 6. Preflight = Browser asking permission before actual request
 * 7. OPTIONS method = Used for preflight requests
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * NEXT STEPS
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * 1. ✅ Run backend and frontend together
 * 2. ✅ Open browser DevTools and check Network tab
 * 3. ✅ Look for Access-Control-Allow-Origin header
 * 4. ✅ Try commenting out this file and see CORS errors
 * 5. ✅ Uncomment and see errors disappear
 * 6. ✅ Learn about @RestController next
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 */
