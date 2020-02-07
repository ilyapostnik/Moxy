package moxy.compiler.presenterbinder

import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import moxy.MvpProcessor
import moxy.presenter.PresenterType
import javax.lang.model.type.TypeMirror

/**
 * Represents field with `@InjectPresenter` annotation.
 * `PresenterField` inner class will be generated based on data from this class.
 * [tag] and [presenterId] and [presenterType] are annotation parameters.
 * [type] is field type.
 * [name] as field name.
 */
class TargetPresenterField constructor(
        val type: TypeMirror,
        val name: String,
        val tag: String?,
        val presenterType: PresenterType = PresenterType.LOCAL,
        val presenterId: String?
) {
    private val isParametrized = TypeName.get(type) is ParameterizedTypeName
    val typeName: TypeName = if (isParametrized) {
        (TypeName.get(type) as ParameterizedTypeName).rawType
    } else {
        TypeName.get(type)
    }

    val generatedClassName: String get() = name.capitalize() + MvpProcessor.PRESENTER_BINDER_INNER_SUFFIX

    /**
     * `@ProvidePresenter` method name if it was found for this field
     */
    var presenterProviderMethodName: String? = null

    /**
     * `@ProvidePresenter` method name if it was found for this field
     */
    var presenterProviderType: PresenterType? = PresenterType.LOCAL

    /**
     * `@ProvidePresenterTag` method name if it was found for this field
     */
    var presenterTagProviderMethodName: String? = null
}