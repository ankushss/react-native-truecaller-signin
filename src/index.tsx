import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-truecaller-signin' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const TruecallerSignin =
  NativeModules.TruecallerSigninModule ||
  new Proxy(
    {},
    {
      get() {
        throw new Error(LINKING_ERROR);
      },
    }
  );

export const CONSENT_MODE = {
  POPUP: 'CONSENT_MODE_POPUP',
  FULLSCREEN: 'CONSENT_MODE_FULLSCREEN',
  BOTTOMSHEET: 'CONSENT_MODE_BOTTOMSHEET',
};

export const CONSENT_TITLE_OPTION = {
  LOG_IN: 'SDK_CONSENT_TITLE_LOG_IN',
  GET_STARTED: 'SDK_CONSENT_TITLE_GET_STARTED',
  REGISTER: 'SDK_CONSENT_TITLE_REGISTER',
  SIGN_IN: 'SDK_CONSENT_TITLE_SIGN_IN',
  SIGN_UP: 'SDK_CONSENT_TITLE_SIGN_UP',
  VERIFY: 'SDK_CONSENT_TITLE_VERIFY',
};

export const BUTTON_SHAPES = {
  ROUNDED: 'BUTTON_SHAPE_ROUNDED',
  RECTANGLE: 'BUTTON_SHAPE_RECTANGLE',
};

export const LOGIN_TEXT_PREFIX = {
  TO_GET_STARTED: 'LOGIN_TEXT_PREFIX_TO_GET_STARTED',
  TO_CONTINUE: 'LOGIN_TEXT_PREFIX_TO_CONTINUE',
  TO_PROCEED: 'LOGIN_TEXT_PREFIX_TO_PROCEED',
};

export const LOGIN_TEXT_SUFFIX = {
  PLEASE_LOGIN: 'LOGIN_TEXT_SUFFIX_PLEASE_LOGIN',
  PLEASE_SIGNUP: 'LOGIN_TEXT_SUFFIX_PLEASE_SIGNUP',
  PLEASE_LOGIN_SIGNUP: 'LOGIN_TEXT_SUFFIX_PLEASE_LOGIN_SIGNUP',
  PLEASE_REGISTER: 'LOGIN_TEXT_SUFFIX_PLEASE_REGISTER',
  PLEASE_SIGN_IN: 'LOGIN_TEXT_SUFFIX_PLEASE_SIGN_IN',
  PLEASE_VERIFY_MOBILE_NO: 'LOGIN_TEXT_SUFFIX_PLEASE_VERIFY_MOBILE_NO',
};

export const CTA_TEXT_PREFIX = {
  USE: 'CTA_TEXT_PREFIX_USE',
  CONTINUE_WITH: 'CTA_TEXT_PREFIX_CONTINUE_WITH',
  PROCEED_WITH: 'CTA_TEXT_PREFIX_PROCEED_WITH',
};

export const FOOTER_TYPE = {
  NONE: 'FOOTER_TYPE_NONE',
  CONTINUE: 'FOOTER_TYPE_CONTINUE',
  ANOTHER_METHOD: 'FOOTER_TYPE_ANOTHER_METHOD',
  MANUALLY: 'FOOTER_TYPE_MANUALLY',
  LATER: 'LATER',
};

export const initializeSDK = ({
  consentMode = CONSENT_MODE.BOTTOMSHEET,
  consentTitleOption = CONSENT_TITLE_OPTION.LOG_IN,
  buttonShape = BUTTON_SHAPES.ROUNDED,
  buttonColor = '#000000',
  buttonTextColor = '#ffffff',
  loginTextPrefix = LOGIN_TEXT_PREFIX.TO_GET_STARTED,
  loginTextSuffix = LOGIN_TEXT_SUFFIX.PLEASE_LOGIN,
  ctaTextPrefix = CTA_TEXT_PREFIX.USE,
  footerType = FOOTER_TYPE.NONE,
} = {}) => {
  TruecallerSignin.initializeSDK({
    consentMode,
    consentTitleOption,
    buttonShape,
    buttonColor,
    buttonTextColor,
    loginTextPrefix,
    loginTextSuffix,
    ctaTextPrefix,
    footerType,
  });
};

export const isUsable = () => TruecallerSignin.isUsable();

export const signIn = () => TruecallerSignin.signIn();

export const clearSDK = () => TruecallerSignin.clearSDK();

export default TruecallerSignin;
