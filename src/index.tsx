import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-truecaller-signin' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const TruecallerSignin = NativeModules.TruecallerSignin
  ? NativeModules.TruecallerSignin
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function multiply(a: number, b: number): Promise<number> {
  return TruecallerSignin.multiply(a, b);
}
