package com.nandy.vkchanllenge.ui.presenter;

import android.graphics.Bitmap;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;

import com.nandy.vkchanllenge.BasePresenter;
import com.nandy.vkchanllenge.ui.StickersDialog;
import com.nandy.vkchanllenge.ui.model.BackgroundModel;
import com.nandy.vkchanllenge.ui.model.PostModel;
import com.nandy.vkchanllenge.ui.model.StickersModel;
import com.nandy.vkchanllenge.ui.model.TextModel;
import com.nandy.vkchanllenge.ui.view.PostView;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by yana on 07.09.17.
 */

public class PostPresenter implements BasePresenter, StickersDialog.OnStickerSelectedListener {

    private PostView<PostPresenter> view;

    private BackgroundModel backgroundModel;
    private TextModel textModel;
    private StickersModel stickersModel;
    private PostModel postModel;


    private Disposable stickersSubscription;
    private Disposable postSubscription;

    public PostPresenter(PostView<PostPresenter> view) {
        this.view = view;
    }

    public void setBackgroundModel(BackgroundModel backgroundModel) {
        this.backgroundModel = backgroundModel;
    }

    public void setStickersModel(StickersModel stickersModel) {
        this.stickersModel = stickersModel;
    }

    public void setTextModel(TextModel textModel) {
        this.textModel = textModel;
    }

    public void setPostModel(PostModel postModel) {
        this.postModel = postModel;
    }

    @Override
    public void start() {
        view.setThumbnails(backgroundModel.getThumbnails());
        stickersSubscription = stickersModel.loadStickers().subscribe();
    }

    @Override
    public void destroy() {

        if (stickersSubscription != null && !stickersSubscription.isDisposed()) {
            stickersSubscription.dispose();
        }

        if (postSubscription != null && !postSubscription.isDisposed()) {
            postSubscription.dispose();
        }
    }

    @Override
    public void onStickerSelected(Bitmap bitmap) {
        view.addSticker(stickersModel.createStickerView(bitmap));
    }

    public void onThumbnailSelected(int thumbnailsResId) {
        view.setBackground(backgroundModel.loadBackground(thumbnailsResId));
    }

    public void highlightText(Layout layout) {
        view.highlight(textModel.highlightText(layout));

    }

    public void loadStickers() {
        view.showStickersPopup(stickersModel.getStickers(), this);
    }

    public void post(View view) {
        postSubscription = postModel.post(view).subscribe(success -> PostPresenter.this.view.onPostResult(success));
    }

}
