#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(TruecallerSignin, NSObject)

RCT_EXPORT_MODULE(TruecallerSigninModule);

RCT_EXTERN_METHOD(initializeSDK:(NSString)consentMode withButtonShape:(NSString)buttonShape)

RCT_EXTERN_METHOD(signIn: (RCTPromiseResolveBlock)resolve
                  rejecter: (RCTPromiseRejectBlock)reject
                  )

+ (BOOL)requiresMainQueueSetup
{
    return NO;
}

@end
