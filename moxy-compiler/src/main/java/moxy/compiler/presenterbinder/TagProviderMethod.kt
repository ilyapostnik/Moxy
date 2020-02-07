package moxy.compiler.presenterbinder

import moxy.presenter.PresenterType
import javax.lang.model.type.TypeMirror

/**
 * Represents method annotated with `@ProvidePresenterTag`.
 * [presenterClass] and optional [presenterId] [presenterType] are annotation parameters.
 */
class TagProviderMethod constructor(
    val presenterClass: TypeMirror,
    val methodName: String,
    val presenterId: String?,
    val presenterType: PresenterType? = PresenterType.LOCAL
)