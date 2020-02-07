package moxy.compiler.presenterbinder

import moxy.presenter.PresenterType
import javax.lang.model.type.DeclaredType

/**
 * Represents method annotated with `@ProvidePresenter`.
 * [tag] and [presenterId] and [presenterType] are annotation parameters.
 */
class PresenterProviderMethod constructor(
        val returnType: DeclaredType,
        val methodName: String,
        val tag: String?,
        val presenterType: PresenterType = PresenterType.LOCAL,
        val presenterId: String?
)