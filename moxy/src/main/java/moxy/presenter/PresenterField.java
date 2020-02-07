package moxy.presenter;

import moxy.MvpPresenter;

public abstract class PresenterField<PresentersContainer> {
    protected final String tag;
    protected final PresenterType presenterType;
    protected final String presenterId;
    protected final Class<? extends MvpPresenter> presenterClass;

    protected PresenterField(String tag, PresenterType presenterType, String presenterId, Class<? extends MvpPresenter<?>> presenterClass) {
        this.tag = tag;
        this.presenterType = presenterType;
        this.presenterId = presenterId;
        this.presenterClass = presenterClass;
    }

    public abstract void bind(PresentersContainer container, MvpPresenter presenter);

    // Delegated may be used from the generated code if a user plans to generate the tag at runtime
    public String getTag(PresentersContainer delegated) {
        return tag;
    }

    public PresenterType getPresenterType() {
        return presenterType;
    }

    public String getPresenterId() {
        return presenterId;
    }

    public Class<? extends MvpPresenter> getPresenterClass() {
        return presenterClass;
    }

    public abstract MvpPresenter<?> providePresenter(PresentersContainer delegated);
}
