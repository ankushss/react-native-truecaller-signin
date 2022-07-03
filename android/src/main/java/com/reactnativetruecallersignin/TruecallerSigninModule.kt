package com.reactnativetruecallersignin

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.facebook.react.bridge.*
import com.truecaller.android.sdk.*

class TruecallerSigninModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {
  private var promise: Promise? = null

  override fun getName(): String = "TruecallerSigninModule"

  private val mActivityEventListener = object : BaseActivityEventListener() {
    override fun onActivityResult(
      activity: Activity?,
      requestCode: Int,
      resultCode: Int,
      intent: Intent?
    ) {
      if (requestCode == TruecallerSDK.SHARE_PROFILE_REQUEST_CODE) {
        TruecallerSDK.getInstance()
          .onActivityResultObtained(activity as FragmentActivity, requestCode, resultCode, intent)
      }
    }
  }

  private val sdkCallback: ITrueCallback = object : ITrueCallback {
    override fun onSuccessProfileShared(trueProfile: TrueProfile) {
      if (promise != null) {
        val params = Arguments.createMap().apply {
          putBoolean("successful", true)
          putString("firstName", trueProfile.firstName)
          putString("lastName", trueProfile.lastName)
          putString("phoneNumber", trueProfile.phoneNumber)
          putString("gender", trueProfile.gender)
          putString("street", trueProfile.street)
          putString("city", trueProfile.city)
          putString("zipcode", trueProfile.zipcode)
          putString("countryCode", trueProfile.countryCode)
          putString("facebookId", trueProfile.facebookId)
          putString("twitterId", trueProfile.twitterId)
          putString("email", trueProfile.email)
          putString("url", trueProfile.url)
          putString("avatarUrl", trueProfile.avatarUrl)
          putBoolean("isVerified", trueProfile.isTrueName)
          putBoolean("isAmbassador", trueProfile.isAmbassador)
          putString("companyName", trueProfile.companyName)
          putString("jobTitle", trueProfile.jobTitle)
          putString("payload", trueProfile.payload)
          putString("signature", trueProfile.signature)
          putString("signatureAlgorithm", trueProfile.signatureAlgorithm)
          putString("requestNonce", trueProfile.requestNonce)
          putBoolean("isBusiness", trueProfile.isBusiness)
        }
        promise!!.resolve(params)
      }
    }

    override fun onFailureProfileShared(trueError: TrueError) {
      if (promise != null) {
        var errorReason: String? = null
        when (trueError.errorType) {
          TrueError.ERROR_TYPE_INTERNAL -> errorReason = "ERROR_TYPE_INTERNAL"
          TrueError.ERROR_TYPE_NETWORK -> errorReason = "ERROR_TYPE_NETWORK"
          TrueError.ERROR_TYPE_USER_DENIED -> errorReason = "ERROR_TYPE_USER_DENIED"
          TrueError.ERROR_PROFILE_NOT_FOUND -> errorReason =
            "ERROR_TYPE_UNAUTHORIZED_PARTNER"
          TrueError.ERROR_TYPE_UNAUTHORIZED_USER -> errorReason =
            "ERROR_TYPE_UNAUTHORIZED_USER"
          TrueError.ERROR_TYPE_TRUECALLER_CLOSED_UNEXPECTEDLY -> errorReason =
            "ERROR_TYPE_TRUECALLER_CLOSED_UNEXPECTEDLY"
          TrueError.ERROR_TYPE_TRUESDK_TOO_OLD -> errorReason =
            "ERROR_TYPE_TRUESDK_TOO_OLD"
          TrueError.ERROR_TYPE_POSSIBLE_REQ_CODE_COLLISION -> errorReason =
            "ERROR_TYPE_POSSIBLE_REQ_CODE_COLLISION"
          TrueError.ERROR_TYPE_RESPONSE_SIGNATURE_MISMATCH -> errorReason =
            "ERROR_TYPE_RESPONSE_SIGNATURE_MISSMATCH"
          TrueError.ERROR_TYPE_REQUEST_NONCE_MISMATCH -> errorReason =
            "ERROR_TYPE_REQUEST_NONCE_MISSMATCH"
          TrueError.ERROR_TYPE_INVALID_ACCOUNT_STATE -> errorReason =
            "ERROR_TYPE_INVALID_ACCOUNT_STATE"
          TrueError.ERROR_TYPE_TC_NOT_INSTALLED -> errorReason =
            "ERROR_TYPE_TC_NOT_INSTALLED"
          TrueError.ERROR_TYPE_ACTIVITY_NOT_FOUND -> errorReason =
            "ERROR_TYPE_ACTIVITY_NOT_FOUND"
        }
        promise!!.reject(errorReason)
      }
    }

    override fun onVerificationRequired(trueError: TrueError) {}
  }

  private fun getConsentMode(mode: String?): Int {
    return when (mode) {
      "CONSENT_MODE_POPUP" -> TruecallerSdkScope.CONSENT_MODE_POPUP
      "CONSENT_MODE_FULLSCREEN" -> TruecallerSdkScope.CONSENT_MODE_FULLSCREEN
      "CONSENT_MODE_BOTTOMSHEET" -> TruecallerSdkScope.CONSENT_MODE_BOTTOMSHEET
      else -> TruecallerSdkScope.CONSENT_MODE_BOTTOMSHEET
    }
  }

  private fun getConsentTitleOption(consentTitleOption: String?): Int{
    return when (consentTitleOption) {
      "SDK_CONSENT_TITLE_LOG_IN" -> TruecallerSdkScope.SDK_CONSENT_TITLE_LOG_IN
      "SDK_CONSENT_TITLE_GET_STARTED" -> TruecallerSdkScope.SDK_CONSENT_TITLE_GET_STARTED
      "SDK_CONSENT_TITLE_REGISTER" -> TruecallerSdkScope.SDK_CONSENT_TITLE_REGISTER
      "SDK_CONSENT_TITLE_SIGN_IN" -> TruecallerSdkScope.SDK_CONSENT_TITLE_SIGN_IN
      "SDK_CONSENT_TITLE_SIGN_UP" -> TruecallerSdkScope.SDK_CONSENT_TITLE_SIGN_UP
      "SDK_CONSENT_TITLE_VERIFY" -> TruecallerSdkScope.SDK_CONSENT_TITLE_VERIFY
      else -> TruecallerSdkScope.SDK_CONSENT_TITLE_LOG_IN
    }
  }

  private fun getButtonShape(shape: String?): Int{
    return when (shape) {
      "BUTTON_SHAPE_ROUNDED" -> TruecallerSdkScope.BUTTON_SHAPE_ROUNDED
      "BUTTON_SHAPE_RECTANGLE" -> TruecallerSdkScope.BUTTON_SHAPE_RECTANGLE
      else -> TruecallerSdkScope.BUTTON_SHAPE_ROUNDED
    }
  }

  private fun getLoginTextPrefix(loginTextPrefix: String?):Int{
    return when (loginTextPrefix) {
      "LOGIN_TEXT_PREFIX_TO_GET_STARTED" -> TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_GET_STARTED
      "LOGIN_TEXT_PREFIX_TO_CONTINUE" -> TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_CONTINUE
      "LOGIN_TEXT_PREFIX_TO_PROCEED" -> TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_PROCEED
      else -> TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_GET_STARTED
    }
  }

  private fun getLoginTextSuffix(loginTextSuffix:String?): Int{
    return when (loginTextSuffix) {
      "LOGIN_TEXT_SUFFIX_PLEASE_LOGIN" -> TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_LOGIN
      "LOGIN_TEXT_SUFFIX_PLEASE_SIGNUP" -> TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_SIGNUP
      "LOGIN_TEXT_SUFFIX_PLEASE_LOGIN_SIGNUP" -> TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_LOGIN_SIGNUP
      "LOGIN_TEXT_SUFFIX_PLEASE_REGISTER" -> TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_REGISTER
      "LOGIN_TEXT_SUFFIX_PLEASE_SIGN_IN" -> TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_SIGN_IN
      "LOGIN_TEXT_SUFFIX_PLEASE_VERIFY_MOBILE_NO" -> TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_VERIFY_MOBILE_NO
      else -> TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_LOGIN
    }
  }

  private fun getCtaTextPrefix(ctaTextPrefix: String?): Int{
    return when (ctaTextPrefix) {
      "CTA_TEXT_PREFIX_USE" -> TruecallerSdkScope.CTA_TEXT_PREFIX_USE
      "CTA_TEXT_PREFIX_CONTINUE_WITH" -> TruecallerSdkScope.CTA_TEXT_PREFIX_CONTINUE_WITH
      "CTA_TEXT_PREFIX_PROCEED_WITH" -> TruecallerSdkScope.CTA_TEXT_PREFIX_PROCEED_WITH
      else -> TruecallerSdkScope.CTA_TEXT_PREFIX_USE
    }
  }

  private fun getFooterType(footerType: String?): Int{
    return when (footerType) {
      "FOOTER_TYPE_NONE" -> TruecallerSdkScope.FOOTER_TYPE_NONE
      "FOOTER_TYPE_CONTINUE" -> TruecallerSdkScope.FOOTER_TYPE_CONTINUE
      "FOOTER_TYPE_ANOTHER_METHOD" -> TruecallerSdkScope.FOOTER_TYPE_ANOTHER_METHOD
      "FOOTER_TYPE_MANUALLY" -> TruecallerSdkScope.FOOTER_TYPE_MANUALLY
      "FOOTER_TYPE_LATER" -> TruecallerSdkScope.FOOTER_TYPE_LATER
      else -> TruecallerSdkScope.FOOTER_TYPE_NONE
    }
  }

  @ReactMethod
  fun initializeSDK(sdkOptions: ReadableMap, promise: Promise) {
    val builder = TruecallerSdkScope.Builder(reactApplicationContext, sdkCallback)
      .consentMode(getConsentMode(sdkOptions.getString("consentMode")))
      .consentTitleOption(getConsentTitleOption(sdkOptions.getString("consentTitleOption")))
      .buttonShapeOptions(getButtonShape(sdkOptions.getString("buttonShape")))
      .buttonColor(Color.parseColor(sdkOptions.getString("buttonColor")))
      .buttonTextColor(Color.parseColor(sdkOptions.getString("buttonTextColor")))
      .loginTextPrefix(getLoginTextPrefix(sdkOptions.getString("loginTextPrefix")))
      .loginTextSuffix(getLoginTextSuffix(sdkOptions.getString("loginTextSuffix")))
      .ctaTextPrefix(getCtaTextPrefix(sdkOptions.getString("ctaTextPrefix")))
      .footerType(getFooterType(sdkOptions.getString("footerType")))
      .sdkOptions(TruecallerSdkScope.SDK_OPTION_WITHOUT_OTP)
    val trueScope = TruecallerSdkScope(builder)
    TruecallerSDK.init(trueScope)
    reactApplicationContext.addActivityEventListener(mActivityEventListener)
    promise.resolve(null)
  }

  @ReactMethod
  fun isUsable(promise: Promise){
    val isUsable = TruecallerSDK.getInstance().isUsable
    promise.resolve(isUsable)
  }

  @ReactMethod
  fun signIn(promise: Promise) {
    this.promise = promise
    if (TruecallerSDK.getInstance().isUsable) {
      TruecallerSDK.getInstance()
        .getUserProfile(reactApplicationContext.currentActivity as FragmentActivity)
    }
  }

  @ReactMethod
  fun clearSDK() {
    TruecallerSDK.clear()
  }
}
