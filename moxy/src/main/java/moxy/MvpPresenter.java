package moxy;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import moxy.locators.ViewStateLocator;
import moxy.viewstate.MvpViewState;
import moxy.presenter.PresenterType;

@InjectViewState
public abstract class MvpPresenter<View extends MvpView> {

    private boolean isFirstLaunch = true;
    private String tag;
    private PresenterType mPresenterType;
    private Set<View> views;
    private View viewStateAsView;
    private MvpViewState<View> viewState;
    private Class<? extends MvpPresenter> presenterClass;

    OnDestroyListener coroutineScope;

    public MvpPresenter() {
        Binder.bind(this);

        views = Collections.newSetFromMap(new WeakHashMap<View, Boolean>());
    }

    /**
     * <p>Attach view to a view state or to a presenter (if a view state doesn't exist).</p>
     * <p>If you're using a {@link moxy.MvpDelegate}, you should not call this method directly.
     * It will be called on {@link moxy.MvpDelegate#onAttach()}, if a view is not attached yet.</p>
     *
     * @param view a view to attach
     */
    public void attachView(View view) {
        if (viewState != null) {
            viewState.attachView(view);
        } else {
            views.add(view);
        }
        if (isFirstLaunch) {
            isFirstLaunch = false;

            onFirstViewAttach();
        }
    }

    /**
     * <p>Callback after the first presenter init and view binding. If this
     * presenter instance will have to attach more views in the future, this method
     * will not be called.</p>
     * <p>There you can interact with a {@link #viewState}.</p>
     */
    protected void onFirstViewAttach() {
    }

    /**
     * <p>Detach view from a view state or from a presenter (if a view state doesn't exist).</p>
     * <p>If you're using a {@link moxy.MvpDelegate}, you should not call this method directly.
     * It will be called on {@link MvpDelegate#onDetach()}.</p>
     *
     * @param view a view to detach
     */
    @SuppressWarnings("WeakerAccess")
    public void detachView(View view) {
        if (viewState != null) {
            viewState.detachView(view);
        } else {
            views.remove(view);
        }
    }

    public void destroyView(View view) {
        if (viewState != null) {
            viewState.destroyView(view);
        }
    }

    /**
     * @return views attached to a view state, or attached to a presenter (if a view state doesn't exist)
     */
    @SuppressWarnings("WeakerAccess")
    public Set<View> getAttachedViews() {
        if (viewState != null) {
            return viewState.getViews();
        }

        return views;
    }

    /**
     * @return view state, casted to the view interface for simplicity
     */
    @SuppressWarnings("WeakerAccess")
    public View getViewState() {
        return viewStateAsView;
    }

    /**
     * Set a view state to the presenter
     *
     * @param viewState that implements type, setted as View generic param
     */
    @SuppressWarnings({ "unchecked", "unused" })
    public void setViewState(MvpViewState<View> viewState) {
        viewStateAsView = (View) viewState;
        this.viewState = viewState;
    }

    /**
     * Check if view is in restore state or not
     *
     * @param view a view for check
     * @return true if the view state is in restore state for the given view. False otherwise.
     */
    @SuppressWarnings("unused")
    public boolean isInRestoreState(View view) {
        //noinspection SimplifiableIfStatement
        if (viewState != null) {
            return viewState.isInRestoreState(view);
        }
        return false;
    }

    PresenterType getPresenterType() {
        return mPresenterType;
    }

    void setPresenterType(PresenterType presenterType) {
        mPresenterType = presenterType;
    }

    String getTag() {
        return tag;
    }

    void setTag(String tag) {
        this.tag = tag;
    }

    Class<? extends MvpPresenter> getPresenterClass() {
        return presenterClass;
    }

    void setPresenterClass(Class<? extends MvpPresenter> presenterClass) {
        this.presenterClass = presenterClass;
    }

    /**
     * <p>Called before the reference to this presenter will be cleared and an instance of the presenter
     * will be never used.</p>
     */
    public void onDestroy() {
    }

    private static class Binder {

        static void bind(MvpPresenter presenter) {
            MvpViewState viewState = ViewStateLocator.getViewState(presenter.getClass());

            presenter.viewStateAsView = (MvpView) viewState;
            presenter.viewState = viewState;
        }
    }
}
