# Backend Deployment Setup Guide

This guide explains how to set up automated backend deployment to Render using GitHub Actions.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Render Setup](#render-setup)
- [GitHub Secrets Configuration](#github-secrets-configuration)
- [Deployment Workflow](#deployment-workflow)
- [Troubleshooting](#troubleshooting)

---

## Prerequisites

Before setting up the deployment pipeline, ensure you have:

1. A [Render](https://render.com) account
2. A GitHub repository with admin access
3. Your Spring Boot backend code in the `backend/` directory

---

## Render Setup

### Step 1: Create a Render Account

1. Go to [render.com](https://render.com)
2. Sign up or log in with your GitHub account (recommended for easier integration)

### Step 2: Deploy Your Service

> **Important**: Render doesn't offer Java as a direct runtime option in the UI. Choose one of these methods:

#### Method A: Using Blueprint (render.yaml) - **RECOMMENDED** ✅

This is the easiest method as your repository already has a `render.yaml` file configured.

1. From the Render dashboard, click **"New +"** → **"Blueprint"**

2. Connect your GitHub repository:
   - Select your repository: `react-ts-springboot-starter`
   - Click **"Connect"**

3. Render will automatically detect the `render.yaml` file and show the service configuration:
   ```
   Service: react-ts-springboot-backend
   Type:    Web Service
   ```

4. Review the configuration and click **"Apply"**

5. Select a plan:
   - **Free tier**: Good for testing (spins down after inactivity)
   - **Starter ($7/month)**: Always-on, better for production

6. Render will automatically build and deploy your service using the settings in `render.yaml`

#### Method B: Using Docker (Manual Setup)

If you prefer manual setup, use Docker runtime:

1. From the Render dashboard, click **"New +"** → **"Web Service"**

2. Connect your GitHub repository:
   - If you signed up with GitHub, select your repository
   - Otherwise, connect your GitHub account first

3. Configure the service:
   ```
   Name:              react-ts-springboot-backend
   Region:            Choose closest to your users (e.g., Oregon, Frankfurt)
   Branch:            main
   Root Directory:    backend
   Runtime:           Docker
   ```

4. Render will automatically detect the `Dockerfile` in the `backend/` directory

5. Select a plan:
   - **Free tier**: Good for testing (spins down after inactivity)
   - **Starter ($7/month)**: Always-on, better for production

6. Advanced settings (expand "Advanced" section):

   **Health Check Path:**
   ```
   /api/hello
   ```

7. Click **"Create Web Service"**

### Step 3: Get Render API Credentials

1. **Get API Key:**
   - Go to [Account Settings](https://dashboard.render.com/account) → **"API Keys"**
   - Click **"Create API Key"**
   - Give it a name (e.g., "GitHub Actions Deploy")
   - Copy the API key and save it securely (you won't see it again!)

2. **Get Service ID:**
   - Go to your service dashboard
   - The URL will look like: `https://dashboard.render.com/web/srv-xxxxxxxxxxxxx`
   - Copy the `srv-xxxxxxxxxxxxx` part - this is your **Service ID**

   Alternatively, you can find it in the service settings under **"Service ID"**

---

## GitHub Secrets Configuration

### Step 1: Add Secrets to GitHub

1. Go to your GitHub repository
2. Navigate to **Settings** → **Secrets and variables** → **Actions**
3. Click **"New repository secret"** for each of the following:

#### Required Secrets:

| Secret Name | Description | Example Value |
|------------|-------------|---------------|
| `RENDER_API_KEY` | Your Render API key from Step 3 above | `rnd_xxxxxxxxxxxxxxxxxxxx` |
| `RENDER_SERVICE_ID` | Your Render service ID | `srv-xxxxxxxxxxxxx` |

### Step 2: Set Up Production Environment (Optional but Recommended)

For better security and control:

1. Go to **Settings** → **Environments** → **"New environment"**
2. Name it `production`
3. Add environment protection rules:
   - ✅ Required reviewers (recommended for production)
   - ✅ Wait timer (e.g., 5 minutes before deployment)
   - ✅ Deployment branches: Only `main` branch

4. Add the secrets to this environment if you want environment-specific secrets

---

## Deployment Workflow

### How It Works

The workflow is triggered automatically when:
- Code is pushed to the `main` branch
- Changes are made to files in the `backend/` directory
- Changes are made to `.github/workflows/backend-deploy.yml` or `render.yaml`

### Workflow Steps

1. **Trigger Render Deployment**
   - Triggers deployment via Render API
   - Render builds the application using Maven (as configured in `render.yaml`)

2. **Wait for Deployment**
   - Monitors deployment status (checks every 10 seconds)
   - Waits until deployment is live or fails

3. **Deployment Summary**
   - Displays deployment details and service URL

### Manual Deployment

To manually trigger a deployment:

1. Go to **Actions** tab in your GitHub repository
2. Select **"Backend Deployment"** workflow
3. Click **"Run workflow"**
4. Select `main` branch
5. Click **"Run workflow"**

### Viewing Deployment Status

- **GitHub Actions**: See real-time logs in the Actions tab
- **Render Dashboard**: Monitor deployment progress and logs at [dashboard.render.com](https://dashboard.render.com)

---

## Testing the Deployment

After deployment completes:

1. **Get your service URL** from the deployment summary or Render dashboard
2. **Test the API endpoint:**
   ```bash
   curl https://your-service-name.onrender.com/api/hello
   ```

   Expected response:
   ```json
   {
     "message": "Hello from Spring Boot!",
     "timestamp": "2025-11-23T..."
   }
   ```

3. **Update frontend to use new API URL:**
   - Update `VITE_API_URL` in your Vercel environment variables
   - Should point to: `https://your-service-name.onrender.com`

---

## Troubleshooting

### Common Issues

#### 1. Deployment Fails with "Failed to trigger deployment"

**Cause:** Invalid API key or Service ID

**Solution:**
- Verify `RENDER_API_KEY` is correct in GitHub secrets
- Verify `RENDER_SERVICE_ID` is the correct service ID from Render
- Ensure the API key has deployment permissions

#### 2. Build Fails on Render

**Cause:** Maven build issues

**Solution:**
- Check Render logs for specific build errors
- Verify `pom.xml` is correct
- Ensure Java version matches (should be 17)
- Check that all dependencies are available

#### 3. Health Check Fails

**Cause:** Service not responding on `/api/hello`

**Solution:**
- Verify the endpoint exists in your code
- Check Render service logs for application errors
- Ensure the service has fully started (may take 2-3 minutes)
- Verify CORS settings if calling from frontend

#### 4. Service Spins Down (Free Tier)

**Cause:** Free tier services sleep after 15 minutes of inactivity

**Solution:**
- Upgrade to Starter plan ($7/month) for always-on service
- Or implement a ping service to keep it alive
- Note: First request after sleep takes ~30 seconds

#### 5. Deployment Timeout

**Cause:** Deployment takes longer than expected

**Solution:**
- Check Render dashboard for deployment status
- Increase timeout in workflow (currently 10 minutes)
- Review Render build logs for bottlenecks

### Getting Help

- **Render Support**: [render.com/docs](https://render.com/docs)
- **GitHub Actions Logs**: Check the Actions tab for detailed error messages
- **Render Dashboard Logs**: View real-time application logs

---

## Advanced Configuration

### Environment-Specific Deployments

To add staging environment:

1. Create another web service in Render for staging
2. Add `RENDER_STAGING_SERVICE_ID` to GitHub secrets
3. Create a `develop` branch deployment workflow
4. Point to staging service ID

### Custom Domain

1. In Render dashboard, go to service **Settings** → **Custom Domain**
2. Add your domain (e.g., `api.yourdomain.com`)
3. Update DNS records as instructed by Render
4. Update CORS settings in Spring Boot to allow your domain

### Notifications

Add Slack/Discord/Email notifications by updating the workflow:

```yaml
- name: Notify Success
  if: success()
  run: |
    curl -X POST ${{ secrets.SLACK_WEBHOOK_URL }} \
      -H 'Content-Type: application/json' \
      -d '{"text":"✅ Backend deployed successfully!"}'
```

### Database Integration

If you need a database:

1. Add PostgreSQL/MySQL from Render dashboard
2. Get connection URL from database settings
3. Add to service environment variables:
   ```
   SPRING_DATASOURCE_URL=jdbc:postgresql://...
   SPRING_DATASOURCE_USERNAME=...
   SPRING_DATASOURCE_PASSWORD=...
   ```

---

## Next Steps

1. ✅ Complete this setup
2. ✅ Test a deployment by pushing to main
3. 📝 Update frontend API URL in Vercel
4. 🔒 Set up custom domain (optional)
5. 📊 Configure monitoring and alerts (optional)
6. 🗄️ Add database if needed (optional)

---

## Summary Checklist

- [ ] Render account created
- [ ] Web service created and configured
- [ ] Render API key obtained
- [ ] Service ID obtained
- [ ] GitHub secrets configured (`RENDER_API_KEY`, `RENDER_SERVICE_ID`)
- [ ] Production environment set up (optional)
- [ ] Test deployment successful
- [ ] Health check passing
- [ ] Frontend updated with backend URL

**Congratulations! Your backend is now set up for automated deployment!** 🎉
