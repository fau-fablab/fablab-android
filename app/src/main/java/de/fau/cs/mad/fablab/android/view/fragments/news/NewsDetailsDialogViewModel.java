package de.fau.cs.mad.fablab.android.view.fragments.news;

import android.os.Bundle;

import javax.inject.Inject;

import de.fau.cs.mad.fablab.android.viewmodel.common.commands.Command;
import de.fau.cs.mad.fablab.rest.core.News;

public class NewsDetailsDialogViewModel {
    public static final String KEY_NEWS = "news";

    private News mNews;

    private Listener mListener;

    private final Command<Void> mImageClickCommand = new Command<Void>() {
        @Override
        public void execute(Void parameter) {
            if (mListener != null && mNews.getLinkToPreviewImage() != null) {
                mListener.onImageClicked();
            }
        }
    };

    private Command<Void> shareCommand = new Command<Void>() {
        @Override
        public void execute(Void parameter) {
            if(mListener != null) {
                mListener.onShareClicked();
            }
        }
    };

    private Command<Void> openInBrowserCommand = new Command<Void>() {
        @Override
        public void execute(Void parameter) {
            if(mListener != null) {
                mListener.onOpenInBrowserClicked();
            }
        }
    };

    @Inject
    public NewsDetailsDialogViewModel() {

    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public Command<Void> getImageClickCommand() {
        return mImageClickCommand;
    }

    public Command<Void> getOpenInBrowserCommand()
    {
        return openInBrowserCommand;
    }

    public Command<Void> getShareCommand()
    {
        return shareCommand;
    }

    public News getNews()
    {
        return mNews;
    }

    public void initialize(Bundle arguments) {
        mNews = (News) arguments.getSerializable(KEY_NEWS);
    }

    public interface Listener {
        void onImageClicked();
        void onShareClicked();
        void onOpenInBrowserClicked();
    }
}
