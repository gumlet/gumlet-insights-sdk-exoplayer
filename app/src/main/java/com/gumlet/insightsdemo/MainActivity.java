package com.gumlet.insightsdemo;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.gumlet.insights.GumletInsightsConfig;
import com.gumlet.insights.data.CustomData;
import com.gumlet.insights.data.PlayerData;
import com.gumlet.insights.data.UserData;
import com.gumlet.insights.data.VideoMetadata;
import com.gumlet.insights.example.Sample;
import com.gumlet.insights.example.Samples;
import com.gumlet.insights.exoplayer.ExoPlayerCollector;
import com.gumlet.insights.utils.GumletLog;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, Player.EventListener {

    private SimpleExoPlayer player;
    private PlayerView playerView;
    private Button releaseButton;
    private Button createButton;
    private Button sourceChangeButton;
    private Button setCustomDataButton;


    private DataSource.Factory dataSourceFactory;

    private ExoPlayerCollector gumletAnalytics;
    private GumletInsightsConfig gumletInsightsConfig;
    private ConcatenatingMediaSource mediaSource;

    private Button videoOneButton;
    private Button videoTwoButton;
    private Button videoThreeButton;
    private Button videoFourButton;
    private Button videoFiveButton;
    private Button videoSixButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerView = findViewById(R.id.a_main_exoplayer);
        releaseButton = findViewById(R.id.release_button);
        releaseButton.setOnClickListener(this);
        createButton = findViewById(R.id.create_button);
        createButton.setOnClickListener(this);
        sourceChangeButton = findViewById(R.id.source_change_button);
        sourceChangeButton.setOnClickListener(this);
        setCustomDataButton = findViewById(R.id.set_custom_data);
        setCustomDataButton.setOnClickListener(this);


        videoOneButton = findViewById(R.id.video_one);
        videoOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onVideoOneButtonClick();
            }
        });
        videoTwoButton = findViewById(R.id.video_two);
        videoTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onVideoTwoButtonClick();
            }
        });
        videoThreeButton = findViewById(R.id.video_three);
        videoThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onVideoThreeButtonClick();
            }
        });

        videoFourButton = findViewById(R.id.video_four);
        videoFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onVideoFourButtonClick();
            }
        });

        videoFiveButton = findViewById(R.id.video_five);
        videoFiveButton.setVisibility(View.VISIBLE);
        videoFiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onVideoFiveButtonClick();
            }
        });

        videoSixButton = findViewById(R.id.video_six);
        videoSixButton.setVisibility(View.VISIBLE);
        videoSixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onVideoSixButtonClick();
            }
        });

        dataSourceFactory = new DefaultDataSourceFactory(this, buildHttpDataSourceFactory());
        createPlayer();
    }

    private void onVideoSixButtonClick() {
        gumletAnalytics.detachPlayer();

        MediaSource mediaSource = buildMediaSource(Samples.INSTANCE.getVIDEO_SEVEN_WIDEVINE());
        gumletInsightsConfig.setVideoId("DRMVideo-Six");
        gumletInsightsConfig.setTitle("Video Six");

        gumletAnalytics.attachPlayer(player);
        player.setMediaSource(mediaSource);
    }

    private void onVideoFiveButtonClick() {
        gumletAnalytics.detachPlayer();

        MediaSource mediaSource = buildMediaSource(Samples.INSTANCE.getVIDEO_SIX_WIDEVINE());
        gumletInsightsConfig.setVideoId("DRMVideo-Five");
        gumletInsightsConfig.setTitle("Video Five");
        gumletAnalytics.attachPlayer(player);
        player.setMediaSource(mediaSource);
    }

    private void onVideoFourButtonClick() {

        gumletAnalytics.detachPlayer();

        MediaSource mediaSource = buildMediaSource(Samples.INSTANCE.getVIDEO_FIVE_WIDEVINE());
        gumletInsightsConfig.setVideoId("DRMVideo-Four");
        gumletInsightsConfig.setTitle("Video Four");

        gumletAnalytics.attachPlayer(player);
        player.setMediaSource(mediaSource);
    }

    private void onVideoThreeButtonClick() {
        gumletAnalytics.detachPlayer();

        MediaSource mediaSource = buildMediaSource(Samples.INSTANCE.getVIDEO_FOUR_WIDEVINE());
        gumletInsightsConfig.setVideoId("DRMVideo-Three");
        gumletInsightsConfig.setTitle("Video Three");

        gumletAnalytics.attachPlayer(player);
        player.setMediaSource(mediaSource);

    }

    private void onVideoTwoButtonClick() {
        gumletAnalytics.detachPlayer();

        MediaSource mediaSource = buildMediaSource(Samples.INSTANCE.getVIDEO_THREE_WIDEVINE());
        gumletInsightsConfig.setVideoId("DRMVideo-Two");
        gumletInsightsConfig.setTitle("Video Two");

        gumletAnalytics.attachPlayer(player);
        player.setMediaSource(mediaSource);

    }

    private void onVideoOneButtonClick() {
        gumletAnalytics.detachPlayer();

        MediaSource mediaSource = buildMediaSource(Samples.INSTANCE.getVIDEO_TWO());
        gumletInsightsConfig.setVideoId("DRMVideo-One");
        gumletInsightsConfig.setTitle("Video One");

        gumletAnalytics.attachPlayer(player);
        player.setMediaSource(mediaSource);

    }

    private int oldIndex = 0;

    @Override
    public void onPositionDiscontinuity(int reason) {
        int sourceIndex = player.getCurrentWindowIndex();
        if (sourceIndex != oldIndex) {
            if (oldIndex >= 0) {
                mediaSource.removeMediaSource(
                        oldIndex,
                        new Handler(),
                        () -> {
                           GumletLog.d("Mainactivity", "isPlaying: " + player.isPlaying());
                           GumletLog.d("Mainactivity", "playbackState: " + player.getPlaybackState());
                           GumletLog.d("Mainactivity", "playWhenReady: " + player.getPlayWhenReady());
                            gumletAnalytics.attachPlayer(player);
                        });
            }
            oldIndex = sourceIndex;
        }
    }

    private void createPlayer() {

        if (player == null) {
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter.Builder(this).build();

            SimpleExoPlayer.Builder exoBuilder = new SimpleExoPlayer.Builder(this);
            exoBuilder.setBandwidthMeter(bandwidthMeter);

            player = exoBuilder.build();

            player.addListener(this);

            // Step 1: Create your insights config object
            // Please use a Valid <PROPERTY-ID> #L8pLClZd
            gumletInsightsConfig = new GumletInsightsConfig("PROPERTY",this);

            // Step 2:
            gumletInsightsConfig.setTitle("EXOPLAYER INSIGHTS");

            // OPTIONAL - SET CUSTOM DATA
            CustomData customData = new CustomData();
            customData.setCustomData1("CUSTOM DATA 1");
            customData.setCustomData2("CUSTOM DATA 2");
            customData.setCustomData3("CUSTOM DATA 3");
            customData.setCustomData4("CUSTOM DATA 4");
            customData.setCustomData5("CUSTOM DATA 5");
            customData.setCustomData6("CUSTOM DATA 6");
            customData.setCustomData7("CUSTOM DATA 7");
            customData.setCustomData8("CUSTOM DATA 8");
            customData.setCustomData9("CUSTOM DATA 9");
            customData.setCustomData10("CUSTOM DATA 10");
            gumletInsightsConfig.setCustomData(customData);

            // OPTIONAL - SET PLAYER NAME AND VERSION
            PlayerData playerData = new PlayerData();
            playerData.setPlayerName("PRO PLAYER");
            playerData.setMetaPageType("IFRAME");
            playerData.setPlayerIntegrationVersion("1.5.6");
            gumletInsightsConfig.setPlayerData(playerData);

            // OPTIONAL - SET USER CUSTOM DATA
            UserData userData = new UserData();
            userData.setCustomUserId("CUSTOM USER ID");
            userData.setName("TEST USER");
            userData.setEmail("DEMO@gumlet.com");
            userData.setPhone("+91 9999999999");
            userData.setProfileImage("ProfileImage.png");
            userData.setAddressLine1("ADDRESS 1");
            userData.setAddressLine2("ADDRESS 2");
            userData.setCity("CITY");
            userData.setState("STATE");
            userData.setCountry("COUNTRY");
            userData.setZipCode("ZIPCODE");
            gumletInsightsConfig.setUserData(userData);

            // OPTIONAL - SET VIDEO METADATA
            VideoMetadata videoMetadata = new VideoMetadata();
            videoMetadata.setCustomContentTye("CONTENT TYPE");
            videoMetadata.setCustomVideoDurationMillis(10000L);
            videoMetadata.setCustomEncodingVariant("ENCODING VARIANT");
            videoMetadata.setCustomVideoLanguage("VIDEO LANGUAGE");
            videoMetadata.setCustomVideoId("VIDEO ID");
            videoMetadata.setCustomVideoSeries("VIDEO SERIES");
            videoMetadata.setCustomVideoProducer("VIDEO PRODUCER");
            videoMetadata.setCustomVideoTitle("VIDEO TITLE");
            videoMetadata.setCustomVideoVariantName("VIDEO VARIENT NAME");
            videoMetadata.setCustomVideoVariant("VIDEO VARIANT");
            gumletInsightsConfig.setVideoMetadata(videoMetadata);


            // Step 3: Create Analytics Collector
            gumletAnalytics = new ExoPlayerCollector(gumletInsightsConfig, MainActivity.this);

            // Step 4: Attach ExoPlayer
            gumletAnalytics.attachPlayer(player);

            // Step 5: Create, prepare, and play media source
            playerView.setPlayer(player);

            //MediaSource mediaSource = buildMediaSource(Samples.INSTANCE.getHLS_DRM_WIDEVINE());
            MediaSource mediaSource = buildMediaSource(Samples.INSTANCE.getVIDEO_ONE());
            // this.mediaSource = new ConcatenatingMediaSource(mediaSource, mediaSource);

            player.setMediaSource(mediaSource);
            player.setPlayWhenReady(false);
            // without prepare() it will not start autoplay
            // prepare also starts preloading data before user clicks play
            // player.prepare();
        }
    }

    private MediaSource buildMediaSource(Sample sample) {
        Uri uri = sample.getUri();
        int type = Util.inferContentType(uri);
        MediaItem.Builder builder = MediaItem.fromUri(uri).buildUpon();

        if (sample.getDrmScheme() != null && sample.getDrmLicenseUri() != null) {
            builder.setDrmUuid(Util.getDrmUuid(sample.getDrmScheme()));
            builder.setDrmLicenseUri(sample.getDrmLicenseUri());
        }

        MediaItem mediaItem = builder.build();

        MediaSourceFactory factory;
        switch (type) {
            case C.TYPE_DASH:
                factory = new DashMediaSource.Factory(dataSourceFactory);
                break;
            case C.TYPE_SS:
                factory = new SsMediaSource.Factory(dataSourceFactory);
                break;
            case C.TYPE_HLS:
                factory = new HlsMediaSource.Factory(dataSourceFactory);
                break;
            case C.TYPE_OTHER:
                factory = new ProgressiveMediaSource.Factory(dataSourceFactory);
                break;
            default:
                throw new IllegalStateException("Unsupported type: " + type);
        }

        return factory.createMediaSource(mediaItem);
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            gumletAnalytics.detachPlayer();
            player = null;
        }
    }

    private HttpDataSource.Factory buildHttpDataSourceFactory() {
        return new DefaultHttpDataSource.Factory()
                .setUserAgent(Util.getUserAgent(this, getString(R.string.app_name)));
    }

    @Override
    public void onClick(View v) {
        if (v == releaseButton) {
            releasePlayer();
        } else if (v == createButton) {
            createPlayer();
        } else if (v == sourceChangeButton) {
            changeSource();
        } else if (v == setCustomDataButton) {
            setCustomData();
        }
    }

    private void changeSource() {
        gumletAnalytics.detachPlayer();

        MediaSource mediaSource = buildMediaSource(Samples.INSTANCE.getVIDEO_THREE_WIDEVINE());
        gumletInsightsConfig.setVideoId("DRMVideo-id");
        gumletInsightsConfig.setTitle("DRM Video Title");

        gumletAnalytics.attachPlayer(player);
        player.setMediaSource(mediaSource);
    }

    private void setCustomData() {
        CustomData customData = gumletAnalytics.getCustomData();
        customData.setCustomData1("custom_data_1_changed");
        customData.setCustomData2("custom_data_2_changed");
        gumletAnalytics.setCustomData(customData);
    }


    @Override
    protected void onResume() {

        if(player != null && !player.isPlaying()){
        }
        super.onResume();
    }

    @Override
    protected void onPause() {

        if(player != null){
            player.pause();
        }
        super.onPause();
    }
}
