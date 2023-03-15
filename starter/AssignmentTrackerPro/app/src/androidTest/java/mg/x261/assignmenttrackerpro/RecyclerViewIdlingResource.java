package mg.x261.assignmenttrackerpro;

import androidx.test.espresso.IdlingResource;

public class RecyclerViewIdlingResource implements IdlingResource {

    private ResourceCallback mCallback;
    private boolean mIsIdle;

    @Override
    public String getName() {
        return RecyclerViewIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback = callback;
    }

    public void setIdleState(boolean isIdle) {
        mIsIdle = isIdle;
        if (mIsIdle && mCallback != null) {
            mCallback.onTransitionToIdle();
        }
    }
}
