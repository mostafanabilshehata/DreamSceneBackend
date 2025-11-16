# Cloudinary Setup Guide for Railway Deployment

## Overview
Your DreamScene application now uses **Cloudinary** for cloud-based image storage instead of local file system. This is required for Railway deployment because Railway has an ephemeral file system.

## Why Cloudinary?
- ✅ **FREE Tier**: 25GB storage, 25GB bandwidth/month
- ✅ **Persistent Storage**: Images survive server restarts
- ✅ **Global CDN**: Fast image delivery worldwide
- ✅ **Automatic Optimization**: Smart image compression
- ✅ **Production Ready**: Scalable cloud infrastructure

---

## Step 1: Create Cloudinary Account

1. Go to **https://cloudinary.com/**
2. Click **"Sign Up for Free"**
3. Create account with:
   - Email address
   - Password
   - OR sign up with Google/GitHub

4. Verify your email address

---

## Step 2: Get Your Cloudinary Credentials

1. Login to Cloudinary Dashboard: **https://cloudinary.com/console**

2. On the dashboard homepage, you'll see:
   ```
   ┌─────────────────────────────────────┐
   │ Account Details                     │
   ├─────────────────────────────────────┤
   │ Cloud Name:    your-cloud-name      │
   │ API Key:       123456789012345      │
   │ API Secret:    abcdefGHIJKLMNOP     │
   └─────────────────────────────────────┘
   ```

3. **Copy these three values** - you'll need them for Railway

---

## Step 3: Add Environment Variables to Railway

### 3.1 Open Railway Dashboard
1. Go to **https://railway.app/dashboard**
2. Select your **DreamScene Backend** project
3. Click on your backend service

### 3.2 Add Cloudinary Variables
1. Click on **"Variables"** tab
2. Click **"New Variable"** button
3. Add these three variables:

| Variable Name | Value | Example |
|--------------|-------|---------|
| `CLOUDINARY_CLOUD_NAME` | Your cloud name from Cloudinary | `demo-cloud` |
| `CLOUDINARY_API_KEY` | Your API key from Cloudinary | `123456789012345` |
| `CLOUDINARY_API_SECRET` | Your API secret from Cloudinary | `abcdefGHIJKLMNOP` |

### 3.3 Save Variables
- Railway will **automatically redeploy** your backend with new variables
- Wait 2-3 minutes for deployment to complete

---

## Step 4: Verify Deployment

### 4.1 Check Railway Logs
1. In Railway, go to **"Deployments"** tab
2. Click on the latest deployment
3. Check logs for successful startup:
   ```
   Started DreamSceneBackendApplication in X.XXX seconds
   ```

### 4.2 Test Image Upload
1. Login to your admin dashboard
2. Try to create/edit a category, subcategory, or item
3. Upload an image
4. If successful, you'll see: **"Image uploaded successfully"**

---

## Step 5: Update Frontend (Already Done)

The frontend is already configured to work with Cloudinary. The image URLs returned from the backend will look like:

```
https://res.cloudinary.com/your-cloud-name/image/upload/v1234567890/dreamscene/abc123.jpg
```

---

## What Changed in the Code

### Backend Changes:

1. **`pom.xml`** - Added Cloudinary dependency:
   ```xml
   <dependency>
       <groupId>com.cloudinary</groupId>
       <artifactId>cloudinary-http44</artifactId>
       <version>1.36.0</version>
   </dependency>
   ```

2. **`application.properties`** - Added Cloudinary config:
   ```properties
   cloudinary.cloud-name=${CLOUDINARY_CLOUD_NAME}
   cloudinary.api-key=${CLOUDINARY_API_KEY}
   cloudinary.api-secret=${CLOUDINARY_API_SECRET}
   ```

3. **`CloudinaryConfig.java`** - Created Cloudinary bean configuration

4. **`FileUploadController.java`** - Changed from local file storage to Cloudinary upload

---

## Troubleshooting

### Error: "Failed to upload image"
**Cause**: Environment variables not set correctly

**Solution**:
1. Verify all 3 Cloudinary variables are added to Railway
2. Check spelling of variable names (case-sensitive)
3. Ensure no extra spaces in values
4. Redeploy backend service

### Error: "Could not autowire Cloudinary"
**Cause**: Maven dependency not downloaded

**Solution**:
1. Check Railway build logs for Maven errors
2. Ensure `pom.xml` has Cloudinary dependency
3. Force rebuild by pushing a new commit

### Images Upload But Don't Display
**Cause**: CORS or Cloudinary URL issue

**Solution**:
1. Check browser console for CORS errors
2. Verify image URLs start with `https://res.cloudinary.com/`
3. Check Cloudinary dashboard for uploaded images

---

## Cloudinary Dashboard Features

### View Uploaded Images
1. Go to **Media Library** in Cloudinary dashboard
2. You'll see all uploaded images in the `dreamscene` folder
3. Click any image to see details and transformations

### Monitor Usage
1. Go to **Dashboard** → **Usage**
2. Check:
   - Storage used (limit: 25GB)
   - Bandwidth used (limit: 25GB/month)
   - Transformations (limit: 25,000/month)

### Delete Images
Images are automatically organized in folders:
- `dreamscene/` - All application images

---

## Free Tier Limits

| Resource | Free Tier Limit |
|----------|----------------|
| Storage | 25 GB |
| Monthly Bandwidth | 25 GB |
| Monthly Transformations | 25,000 |
| Monthly Credits | 0 credits |
| Storage Videos | 25 GB |

**For most small-to-medium applications, this is more than enough!**

---

## Next Steps

1. ✅ Create Cloudinary account
2. ✅ Copy Cloud Name, API Key, API Secret
3. ✅ Add environment variables to Railway
4. ✅ Wait for Railway to redeploy
5. ✅ Test image upload in admin dashboard
6. ✅ Celebrate! 🎉

---

## Support

### Cloudinary Support
- Documentation: https://cloudinary.com/documentation
- Community: https://community.cloudinary.com/
- Email: support@cloudinary.com

### Railway Support
- Documentation: https://docs.railway.app/
- Discord: https://discord.gg/railway

---

## Summary

Your DreamScene backend now uses Cloudinary for cloud image storage, making it production-ready for Railway deployment. All image uploads will be stored permanently in the cloud and accessible via CDN URLs.

**Admin Password**: username `admin` / password `admin123`

Good luck with your deployment! 🚀
