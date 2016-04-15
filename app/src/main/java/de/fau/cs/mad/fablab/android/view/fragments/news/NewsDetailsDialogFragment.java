package de.fau.cs.mad.fablab.android.view.fragments.news;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;
import de.fau.cs.mad.fablab.android.R;
import de.fau.cs.mad.fablab.android.view.activities.MainActivity;
import de.fau.cs.mad.fablab.android.view.common.binding.MenuItemCommandBinding;
import de.fau.cs.mad.fablab.android.view.common.binding.ViewCommandBinding;
import de.fau.cs.mad.fablab.android.view.common.fragments.BaseFragment;
import de.fau.cs.mad.fablab.android.viewmodel.common.ObservableWebView;

public class NewsDetailsDialogFragment extends BaseFragment
        implements NewsDetailsDialogViewModel.Listener {
    @Bind(R.id.news_dialog_title)
    TextView title_tv;
    @Bind(R.id.news_dialog_webview)
    ObservableWebView webView;
    @Bind(R.id.news_dialog_image)
    ImageView image_iv;

    LinearLayout title_ll;
    LinearLayout header_ll;

    @Inject
    NewsDetailsDialogViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel.setListener(this);
        mViewModel.initialize(getArguments());

        new ViewCommandBinding().bind(image_iv, mViewModel.getImageClickCommand());

        title_ll = (LinearLayout) getActivity().findViewById(R.id.news_dialog_title_ll);
        header_ll = (LinearLayout) getActivity().findViewById(R.id.news_dialog_header_ll);

        title_tv.setText(mViewModel.getNews().getTitle());

        String stylesheet = "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_dialog_stylesheet.css\" /> ";
        String htmlData = stylesheet + "<div class=\"webview_content\">" + mViewModel.getNews().getDescription() + "</div>";

        webView.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
        if (mViewModel.getNews().getLinkToPreviewImage() != null) {
            Picasso.with(image_iv.getContext()).load(mViewModel.getNews().getLinkToPreviewImage()).into(image_iv);
        } else {
            Picasso.with(image_iv.getContext()).load(R.drawable.news_nopicture).fit().into(image_iv);
        }

        header_ll.bringToFront();

        final LinearLayout.LayoutParams lp_overOffset = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp_overOffset.setMargins(0, 0, 0, 180);

        webView.setOnScrollChangedCallback(new ObservableWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int l, int t) {
                if(t < 180) {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    lp.setMargins(0, 0, 0, t);
                    title_ll.setLayoutParams(lp);
                } else {
                    title_ll.setLayoutParams(lp_overOffset);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        return inflater.inflate(R.layout.fragment_news_dialog, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        int displayOptions = MainActivity.DISPLAY_LOGO | MainActivity.DISPLAY_NAVDRAWER;
        setDisplayOptions(displayOptions);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_share, menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        new MenuItemCommandBinding().bind(shareItem, mViewModel.getShareCommand());

        MenuItem openInBrowserItem = menu.findItem(R.id.action_open);
        new MenuItemCommandBinding().bind(openInBrowserItem, mViewModel.getOpenInBrowserCommand());

    }

    @Override
    public void onImageClicked() {
        final Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ImageView imageView = new ImageView(getActivity());
        Picasso.with(imageView.getContext()).load(mViewModel.getNews().getLinkToPreviewImage()).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

    @Override
    public void onShareClicked()
    {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mViewModel.getNews().getTitle());
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, mViewModel.getNews().getLink());
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share)));
    }

    @Override
    public void onOpenInBrowserClicked()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(mViewModel.getNews().getLink()));
        startActivity(intent);
    }
}
