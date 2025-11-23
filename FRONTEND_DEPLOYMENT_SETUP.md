# Frontend Deployment Setup Guide

This guide explains how to set up automated frontend deployment to Vercel using GitHub Actions.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Vercel Setup](#vercel-setup)
- [GitHub Secrets Configuration](#github-secrets-configuration)
- [Deployment Workflow](#deployment-workflow)
- [Troubleshooting](#troubleshooting)

---

## Prerequisites

Before setting up the deployment pipeline, ensure you have:

1. A [Vercel](https://vercel.com) account
2. A GitHub repository with admin access
3. Your React TypeScript frontend code in the `frontend/` directory
4. Backend API deployed and accessible (see [BACKEND_DEPLOYMENT_SETUP.md](./BACKEND_DEPLOYMENT_SETUP.md))

---

## Vercel Setup

### Step 1: Create a Vercel Account

1. Go to [vercel.com](https://vercel.com)
2. Click **"Sign Up"**
3. Sign up with your GitHub account (recommended for easier integration)
4. Authorize Vercel to access your GitHub repositories

### Step 2: Import Your Project

1. From the Vercel dashboard, click **"Add New..."** → **"Project"**

2. Import your GitHub repository:
   - Find and select your repository from the list
   - Click **"Import"**

3. Configure the project:
   ```
   Project Name:       Your project name (e.g., my-app-frontend)
   Framework Preset:   Vite
   Root Directory:     frontend
   ```

4. Build and Output Settings:
   ```
   Build Command:      npm run build
   Output Directory:   dist
   Install Command:    npm install
   ```

5. **Environment Variables** (click "Add" for each):

   | Name | Value | Environment |
   |------|-------|-------------|
   | `VITE_API_URL` | `https://your-backend.onrender.com` | Production |

   > **Note**: Replace `https://your-backend.onrender.com` with your actual Render backend URL

6. Click **"Deploy"**

   Vercel will now build and deploy your application. This first deployment helps verify everything works.

### Step 3: Get Vercel Credentials for GitHub Actions

After your first deployment succeeds, you need to collect credentials for automated deployments.

#### 3.1 Get Vercel Token

1. Go to [Account Settings](https://vercel.com/account/tokens) → **"Tokens"**
2. Click **"Create Token"**
3. Configure the token:
   ```
   Token Name:  GitHub Actions Deploy
   Scope:       Full Account
   Expiration:  No Expiration (or set as needed)
   ```
4. Click **"Create Token"**
5. **Copy the token immediately** and save it securely (you won't see it again!)

#### 3.2 Get Organization ID

1. Go to [Account Settings](https://vercel.com/account)
2. Scroll down to **"Your ID"** section
3. Copy the **Organization ID** (starts with `team_` or `user_`)

   Alternatively, use Vercel CLI:
   ```bash
   npm i -g vercel
   vercel login
   vercel link
   cat .vercel/project.json
   ```

#### 3.3 Get Project ID

**Method 1: From Vercel Dashboard**
1. Go to your project settings
2. Navigate to **Settings** → **General**
3. Scroll down to find **"Project ID"**
4. Copy the Project ID

**Method 2: From .vercel folder (if linked locally)**
```bash
cat .vercel/project.json
```

The file will show:
```json
{
  "orgId": "team_xxxxxxxxxxxx",
  "projectId": "prj_xxxxxxxxxxxx"
}
```

---

## GitHub Secrets Configuration

### Step 1: Add Secrets to GitHub

1. Go to your GitHub repository
2. Navigate to **Settings** → **Secrets and variables** → **Actions**
3. Click **"New repository secret"** for each of the following:

#### Required Secrets:

| Secret Name | Description | Example Value |
|------------|-------------|---------------|
| `VERCEL_TOKEN` | Your Vercel authentication token | `xxxxxxxxxxxxxxxxxxxxxx` |
| `VERCEL_ORG_ID` | Your Vercel organization/team ID | `team_xxxxxxxxxxxx` or `user_xxxxxxxxxxxx` |
| `VERCEL_PROJECT_ID` | Your Vercel project ID | `prj_xxxxxxxxxxxx` |
| `PROD_API_URL` | Your backend API URL (production) - used for deployment reference | `https://react-ts-springboot-backend.onrender.com` |

### Step 2: Set Up Production Environment (Optional but Recommended)

For better security and deployment control:

1. Go to **Settings** → **Environments** → **"New environment"**
2. Name it `production`
3. Add environment protection rules:
   - ✅ Required reviewers (recommended for production)
   - ✅ Wait timer (e.g., 5 minutes before deployment)
   - ✅ Deployment branches: Only `main` branch

4. Add environment-specific secrets if needed

---

## Deployment Workflow

### How It Works

The workflow is triggered automatically when:
- Code is pushed to the `main` branch
- Changes are made to files in the `frontend/` directory
- Changes are made to `.github/workflows/frontend-deploy.yml`

### Workflow Steps

1. **Checkout Code**: Gets the latest code from repository
2. **Setup Node.js**: Installs Node.js 18 with npm caching
3. **Install Vercel CLI**: Installs the latest Vercel CLI globally
4. **Pull Vercel Environment**: Downloads production environment configuration
5. **Build Project**: Builds the Vite application for production
6. **Deploy to Vercel**: Deploys the pre-built application to production
7. **Display Summary**: Shows deployment URL and details

### Environment Variables in Build

The workflow automatically injects environment variables during build:
- `VITE_API_URL`: Points to your production backend API

These are configured in Vercel project settings and pulled during the build step.

### Manual Deployment

To manually trigger a deployment:

1. Go to **Actions** tab in your GitHub repository
2. Select **"Frontend CI/CD"** workflow
3. Click **"Run workflow"**
4. Select `main` branch
5. Click **"Run workflow"**

### Automatic Deployments

Every push to `main` automatically:
1. Builds the frontend
2. Deploys to Vercel production
3. Updates your live site
4. Provides deployment URL in the workflow summary

---

## Vercel Project Configuration

### Environment Variables in Vercel

Set these in Vercel Dashboard → **Settings** → **Environment Variables**:

| Variable | Value | Environments |
|----------|-------|--------------|
| `VITE_API_URL` | `https://react-ts-springboot-backend.onrender.com` | Production |
| `VITE_API_URL` | `https://staging-backend.onrender.com` | Preview (optional) |
| `VITE_API_URL` | `http://localhost:8080` | Development (optional) |

> **Important**:
> - All Vite environment variables must be prefixed with `VITE_` to be exposed to the browser
> - The GitHub secret is named `PROD_API_URL` for reference, but in Vercel it must be `VITE_API_URL`
> - Vercel injects these variables during the build process

### Build Settings Verification

Verify these settings in Vercel Dashboard → **Settings** → **Build & Development Settings**:

```
Framework Preset:     Vite
Build Command:        npm run build
Output Directory:     dist
Install Command:      npm install
Development Command:  npm run dev
```

### Root Directory

Ensure **Root Directory** is set to `frontend` in:
**Settings** → **General** → **Root Directory**

---

## Testing the Deployment

After deployment completes:

### 1. Get Your Deployment URL

From the GitHub Actions workflow summary or Vercel dashboard:
```
https://your-project.vercel.app
```

### 2. Test the Application

1. **Visit the URL** in your browser
2. **Check API connectivity**: The app should successfully fetch data from your backend
3. **Verify environment variables**: Open browser console and check that API calls go to the correct backend URL

### 3. Test API Integration

Open browser DevTools → Network tab:
```
Expected API calls to: https://your-backend.onrender.com/api/...
```

If API calls fail, verify:
- Backend is deployed and running
- `VITE_API_URL` is set correctly in Vercel
- CORS is configured in backend to allow your Vercel domain

---

## Troubleshooting

### Common Issues

#### 1. Build Fails with "Command failed"

**Cause:** TypeScript errors or dependency issues

**Solution:**
```bash
# Test build locally
cd frontend
npm install
npm run build

# Check for TypeScript errors
npx tsc --noEmit
```

Fix any errors before pushing.

#### 2. Environment Variables Not Working

**Cause:** Variables not prefixed with `VITE_` or not set in Vercel

**Solution:**
- All client-side variables must start with `VITE_`
- Set variables in Vercel Dashboard → Settings → Environment Variables
- Redeploy after adding variables
- Variables are injected at build time, not runtime

#### 3. API Calls Failing (CORS Errors)

**Cause:** Backend CORS not configured for Vercel domain

**Solution:**
Update your Spring Boot application to allow your Vercel domain:

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins(
                "http://localhost:3000",
                "https://your-project.vercel.app",
                "https://*.vercel.app"  // Allow all preview deployments
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}
```

#### 4. "Invalid token" Error

**Cause:** Incorrect or expired Vercel token

**Solution:**
- Generate a new token in Vercel account settings
- Update `VERCEL_TOKEN` in GitHub secrets
- Ensure the token has Full Account scope

#### 5. "Project not found" Error

**Cause:** Incorrect Project ID or Organization ID

**Solution:**
- Verify `VERCEL_PROJECT_ID` matches your project
- Verify `VERCEL_ORG_ID` matches your account/team
- Run `vercel link` locally and check `.vercel/project.json`

#### 6. Deployment Succeeds but Site Shows Old Version

**Cause:** Browser caching or build cache issue

**Solution:**
- Hard refresh: Ctrl+Shift+R (Windows/Linux) or Cmd+Shift+R (Mac)
- Clear Vercel build cache: Redeploy with "Clear Cache and Redeploy" in dashboard
- Check deployment URL in Vercel dashboard to verify it's the latest

#### 7. Preview Deployments Not Working

**Cause:** Workflow only deploys on `main` branch

**Solution:**
The current workflow only deploys production on `main` branch. To enable preview deployments:
- Vercel automatically creates preview deployments for all branches/PRs
- Access them from the Vercel dashboard or PR comments
- No GitHub Action needed for previews (Vercel handles this automatically)

### Viewing Deployment Logs

**GitHub Actions Logs:**
1. Go to **Actions** tab
2. Click on the workflow run
3. Expand job steps to see detailed logs

**Vercel Deployment Logs:**
1. Go to [Vercel Dashboard](https://vercel.com/dashboard)
2. Select your project
3. Click on the deployment
4. View **Build Logs** and **Runtime Logs**

### Getting Help

- **Vercel Documentation**: [vercel.com/docs](https://vercel.com/docs)
- **Vercel Support**: Available in dashboard for paid plans
- **GitHub Actions Logs**: Check Actions tab for detailed error messages
- **Vite Documentation**: [vitejs.dev](https://vitejs.dev)

---

## Advanced Configuration

### Custom Domain

1. In Vercel dashboard, go to **Settings** → **Domains**
2. Click **"Add"**
3. Enter your domain (e.g., `app.yourdomain.com`)
4. Follow DNS configuration instructions:
   ```
   Type:  CNAME
   Name:  app (or @ for root domain)
   Value: cname.vercel-dns.com
   ```
5. Wait for DNS propagation (can take up to 48 hours)
6. Vercel automatically provisions SSL certificate

### Multiple Environments

Set up staging and production:

**Staging Branch Deployment:**
```yaml
# .github/workflows/frontend-deploy-staging.yml
on:
  push:
    branches: [develop]

env:
  ENVIRONMENT: preview
```

Update environment variables in Vercel for each environment.

### Performance Optimization

**Enable Speed Insights:**
1. Go to **Analytics** → **Speed Insights** in Vercel dashboard
2. Click **"Enable"**
3. Install package:
   ```bash
   npm install @vercel/speed-insights
   ```
4. Add to your app:
   ```tsx
   import { SpeedInsights } from '@vercel/speed-insights/react'

   function App() {
     return (
       <>
         <YourApp />
         <SpeedInsights />
       </>
     )
   }
   ```

**Enable Web Analytics:**
1. Go to **Analytics** in Vercel dashboard
2. Click **"Enable"**
3. No code changes needed

### Notifications

Add deployment notifications to Slack/Discord:

**Vercel Integration:**
1. Go to **Settings** → **Integrations**
2. Add Slack or Discord integration
3. Configure notification preferences

**GitHub Actions Notification:**
```yaml
- name: Notify Deployment
  if: success()
  run: |
    curl -X POST ${{ secrets.SLACK_WEBHOOK_URL }} \
      -H 'Content-Type: application/json' \
      -d '{"text":"✅ Frontend deployed to ${{ steps.deploy.outputs.url }}"}'
```

### Edge Functions

For server-side logic, use Vercel Edge Functions:

1. Create `api/` folder in `frontend/`
2. Add Edge Function:
   ```typescript
   // frontend/api/hello.ts
   import type { VercelRequest, VercelResponse } from '@vercel/node'

   export default function handler(req: VercelRequest, res: VercelResponse) {
     res.status(200).json({ message: 'Hello from Edge!' })
   }
   ```
3. Deploy and access at: `https://your-app.vercel.app/api/hello`

### Preview Deployments

Vercel automatically creates preview deployments for:
- Every pull request
- Every branch push

**Access Preview URLs:**
- Posted as PR comment by Vercel bot
- Available in Vercel dashboard
- Format: `https://your-app-git-branch-name.vercel.app`

**Configure Preview Environment Variables:**
Set different API URLs for preview deployments in Vercel dashboard.

---

## Security Best Practices

### 1. Environment Variables

- ✅ **Never** commit `.env` files with secrets
- ✅ Use `VITE_` prefix only for public variables
- ✅ Store sensitive keys in Vercel environment variables (not in code)
- ❌ Don't expose API keys or secrets to the browser

### 2. Token Security

- ✅ Use scoped tokens with minimal permissions
- ✅ Set token expiration when possible
- ✅ Rotate tokens periodically
- ✅ Never commit tokens to repository

### 3. Deployment Protection

- ✅ Enable required reviewers for production deployments
- ✅ Use branch protection rules on `main`
- ✅ Enable deployment branch restrictions
- ✅ Review deployment logs regularly

### 4. Content Security

- ✅ Enable HTTPS only (Vercel does this by default)
- ✅ Configure Content Security Policy headers
- ✅ Enable HSTS (HTTP Strict Transport Security)
- ✅ Use environment-specific API URLs

---

## Integration with Backend

### API URL Configuration

**Development (Local):**
```bash
# frontend/.env
VITE_API_URL=
# Empty = uses Vite proxy to http://localhost:8080
```

**Production (Vercel):**
```
VITE_API_URL=https://your-backend.onrender.com
# Set in Vercel Dashboard → Environment Variables
```

### CORS Configuration

Ensure your Spring Boot backend allows requests from Vercel:

```java
// backend/src/main/java/com/example/demo/config/CorsConfig.java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Value("${cors.allowed.origins}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins(allowedOrigins.split(","))
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}
```

```properties
# backend/src/main/resources/application.properties
cors.allowed.origins=http://localhost:3000,https://your-project.vercel.app,https://*.vercel.app
```

### Testing Full Stack Integration

```bash
# 1. Start backend locally
cd backend
mvn spring-boot:run

# 2. Start frontend locally
cd frontend
npm run dev

# 3. Test at http://localhost:3000
```

---

## Deployment Checklist

### Initial Setup
- [ ] Vercel account created
- [ ] Project imported to Vercel
- [ ] First deployment successful
- [ ] Vercel token obtained
- [ ] Organization ID obtained
- [ ] Project ID obtained
- [ ] GitHub secrets configured:
  - [ ] `VERCEL_TOKEN`
  - [ ] `VERCEL_ORG_ID`
  - [ ] `VERCEL_PROJECT_ID`
  - [ ] `PROD_API_URL`
- [ ] Production environment set up (optional)

### Environment Configuration
- [ ] `VITE_API_URL` set in Vercel for production
- [ ] Backend URL is correct and accessible
- [ ] CORS configured in backend for Vercel domain
- [ ] Environment variables tested

### Testing
- [ ] Test deployment successful via GitHub Actions
- [ ] Application loads correctly
- [ ] API integration working
- [ ] No CORS errors
- [ ] No console errors

### Optional Enhancements
- [ ] Custom domain configured
- [ ] SSL certificate auto-provisioned
- [ ] Analytics enabled
- [ ] Speed Insights enabled
- [ ] Notifications configured
- [ ] Preview deployments tested

---

## Monitoring and Maintenance

### Regular Checks

**Weekly:**
- Review deployment logs for errors
- Check analytics for performance issues
- Monitor error rates

**Monthly:**
- Review and rotate access tokens
- Update dependencies (`npm update`)
- Review and optimize build times

**As Needed:**
- Update environment variables when backend URL changes
- Configure new preview environments
- Add team members to Vercel project

### Performance Monitoring

**Vercel Analytics:**
- Real-time visitor data
- Geographic distribution
- Device and browser stats

**Speed Insights:**
- Core Web Vitals tracking
- Performance scores
- Page load metrics

**Logs:**
- Access logs in Vercel dashboard
- Runtime logs for debugging
- Build logs for troubleshooting

---

## Next Steps

1. ✅ Complete this setup
2. ✅ Test a deployment by pushing to main
3. 📝 Verify frontend connects to backend API
4. 🔒 Set up custom domain (optional)
5. 📊 Enable analytics and monitoring (optional)
6. 🎨 Configure preview deployments for staging (optional)
7. 🔔 Set up deployment notifications (optional)

---

## Summary

Your frontend deployment pipeline:

1. **Developer** pushes code to `main`
2. **GitHub Actions** triggers automatically
3. **Vercel CLI** builds the application
4. **Vercel** deploys to production
5. **User** sees updated app at your Vercel URL

**Congratulations! Your frontend is now set up for automated deployment!** 🎉

For backend setup, see [BACKEND_DEPLOYMENT_SETUP.md](./BACKEND_DEPLOYMENT_SETUP.md)
