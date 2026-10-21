@_exported import ExportedKotlinPackages
@_exported import KotlinCoroutineSupport
import KotlinRuntime
import KotlinRuntimeSupport
@_implementationOnly import SharedBridge_OrgJetbrainsKotlinxKotlinxCoroutinesCore

extension ExportedKotlinPackages.kotlinx.coroutines.flow.Flow where Self : ExportedKotlinPackages.kotlinx.coroutines.flow.__Flow {
}
extension ExportedKotlinPackages.kotlinx.coroutines.flow.Flow {
}
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.kotlinx.coroutines.flow.Flow, ExportedKotlinPackages.kotlinx.coroutines.flow.__Flow, KotlinCoroutineSupport.KotlinFlow where Wrapped : ExportedKotlinPackages.kotlinx.coroutines.flow._Flow {
}
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.kotlinx.coroutines.flow._Flow {
}
extension ExportedKotlinPackages.kotlinx.coroutines.flow {
    public protocol Flow: KotlinRuntime.KotlinBase, ExportedKotlinPackages.kotlinx.coroutines.flow._Flow, KotlinCoroutineSupport.KotlinFlow {
    }
    @objc(_Flow)
    public protocol _Flow {
    }
    public protocol __Flow: KotlinRuntimeSupport._KotlinBridgeable {
    }
}
