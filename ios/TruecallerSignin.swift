import TrueSDK

@objc(TruecallerSignin)
class TruecallerSignin: NSObject {
    private var resolve: RCTPromiseResolveBlock?
    private var reject: RCTPromiseRejectBlock?
    
    override init(){
        super.init()
        TCTrueSDK.sharedManager().delegate = self
    }
    
    @objc(initializeSDK:withButtonShape:)
    func initializeSDK(consentMode: String, buttonShape: String){
        if TCTrueSDK.sharedManager().isSupported() {
            let appKey = Bundle.main.object(forInfoDictionaryKey: "TRUECALLER_APP_KEY") as? String
            let appLink = Bundle.main.object(forInfoDictionaryKey: "TRUECALLER_APP_LINK") as? String
            TCTrueSDK.sharedManager().setup(withAppKey: appKey, appLink: appLink)
        }
    }
    
    func signIn(_ resolve: RCTPromiseResolveBlock,
                rejecter reject:RCTPromiseRejectBlock){
        self.resolve = resolve
        self.reject = reject
        TCTrueSDK.sharedManager().requestTrueProfile()
    }
}

extension TruecallerSignin:TCTrueSDKDelegate{
    func didFailToReceiveTrueProfileWithError(_ error: TCError) {
        reject(error)
    }
    
    func didReceive(_ profile: TCTrueProfile) {
        var map: [String: Any] = [:]
        map["successful"] = true
        map["firstName"] = profile.firstName
        map["lastName"] = profile.lastName
        map["phoneNumber"] = profile.phoneNumber
        map["countryCode"] = profile.countryCode
        map["street"] = profile.street
        map["city"] = profile.city
        map["zipcode"] = profile.zipcode
        map["email"] = profile.email
        map["url"] = profile.url
        map["avatarUrl"] = profile.avatarUrl
        resolve(map)
    }
}
