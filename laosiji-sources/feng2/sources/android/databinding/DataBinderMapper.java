package android.databinding;

import android.view.View;
import com.facebook.common.util.UriUtil;
import com.feng.car.R;
import com.feng.car.databinding.ActivityAboutUsBinding;
import com.feng.car.databinding.ActivityAddSubjectBinding;
import com.feng.car.databinding.ActivityAddSubjectHeaderBinding;
import com.feng.car.databinding.ActivityAllCarBinding;
import com.feng.car.databinding.ActivityAllprogramBinding;
import com.feng.car.databinding.ActivityArticleDetailBinding;
import com.feng.car.databinding.ActivityAtMeBinding;
import com.feng.car.databinding.ActivityAudioPlayDetailBinding;
import com.feng.car.databinding.ActivityBaseLayoutBinding;
import com.feng.car.databinding.ActivityBlacklistBinding;
import com.feng.car.databinding.ActivityCameraBinding;
import com.feng.car.databinding.ActivityCameraPreviewBinding;
import com.feng.car.databinding.ActivityCarModelListBinding;
import com.feng.car.databinding.ActivityCarPhotoListBinding;
import com.feng.car.databinding.ActivityCarsPriceRankingNewBinding;
import com.feng.car.databinding.ActivityCircleBinding;
import com.feng.car.databinding.ActivityCityListBinding;
import com.feng.car.databinding.ActivityCommentBinding;
import com.feng.car.databinding.ActivityCommentDetailBinding;
import com.feng.car.databinding.ActivityConfigureCompareBinding;
import com.feng.car.databinding.ActivityCropBinding;
import com.feng.car.databinding.ActivityDistributorMapBinding;
import com.feng.car.databinding.ActivityEditUserInfoBinding;
import com.feng.car.databinding.ActivityEditUserNextBinding;
import com.feng.car.databinding.ActivityFansBinding;
import com.feng.car.databinding.ActivityFeedbackBinding;
import com.feng.car.databinding.ActivityFindFollowBinding;
import com.feng.car.databinding.ActivityGoodsServeLayoutBinding;
import com.feng.car.databinding.ActivityGuideBinding;
import com.feng.car.databinding.ActivityHistoryBinding;
import com.feng.car.databinding.ActivityLevelConditionBinding;
import com.feng.car.databinding.ActivityLoginBinding;
import com.feng.car.databinding.ActivityMainBinding;
import com.feng.car.databinding.ActivityMediaMeasurementDetailBinding;
import com.feng.car.databinding.ActivityNewPersonalHomeBinding;
import com.feng.car.databinding.ActivityNewSearchBinding;
import com.feng.car.databinding.ActivityNewSubjectBinding;
import com.feng.car.databinding.ActivityOpenStoreBinding;
import com.feng.car.databinding.ActivityOpenStoreHeaderBinding;
import com.feng.car.databinding.ActivityPersonalHomeNewBinding;
import com.feng.car.databinding.ActivityPersonalSearchBinding;
import com.feng.car.databinding.ActivityPopularProgramListBinding;
import com.feng.car.databinding.ActivityPreviewLocalimageBinding;
import com.feng.car.databinding.ActivityPriceBinding;
import com.feng.car.databinding.ActivityPriceConditionBinding;
import com.feng.car.databinding.ActivityPriceNewRankingBinding;
import com.feng.car.databinding.ActivityPrivateChatBinding;
import com.feng.car.databinding.ActivityPrivateLetterListBinding;
import com.feng.car.databinding.ActivityPrivateSettingBinding;
import com.feng.car.databinding.ActivityPushBinding;
import com.feng.car.databinding.ActivityPushSecondaryBinding;
import com.feng.car.databinding.ActivityQrcodBinding;
import com.feng.car.databinding.ActivityRearrangeBinding;
import com.feng.car.databinding.ActivityScanningLoginBinding;
import com.feng.car.databinding.ActivitySearchCarBinding;
import com.feng.car.databinding.ActivitySearchCityBinding;
import com.feng.car.databinding.ActivitySearchConditionLayoutBinding;
import com.feng.car.databinding.ActivitySearchRelatedTagBinding;
import com.feng.car.databinding.ActivitySearchResultBinding;
import com.feng.car.databinding.ActivitySearchcarBrandBinding;
import com.feng.car.databinding.ActivitySearchcarLevelBinding;
import com.feng.car.databinding.ActivitySearchcarResultBinding;
import com.feng.car.databinding.ActivitySelectArticleCoverBinding;
import com.feng.car.databinding.ActivitySelectGoodsLayoutBinding;
import com.feng.car.databinding.ActivitySelectPhotoBinding;
import com.feng.car.databinding.ActivitySendCommentNewBinding;
import com.feng.car.databinding.ActivitySendPrivateBinding;
import com.feng.car.databinding.ActivitySettingAccountPhoneBinding;
import com.feng.car.databinding.ActivitySettingAccountSecurityBinding;
import com.feng.car.databinding.ActivitySettingBinding;
import com.feng.car.databinding.ActivityShortvideoCropBinding;
import com.feng.car.databinding.ActivityShortvideoSelectimageBinding;
import com.feng.car.databinding.ActivityShowBigimageBinding;
import com.feng.car.databinding.ActivityShowCarimageBinding;
import com.feng.car.databinding.ActivityShowNativeImageBinding;
import com.feng.car.databinding.ActivitySplashBinding;
import com.feng.car.databinding.ActivityUploadIdcardBinding;
import com.feng.car.databinding.ActivityUploadPriceImageBinding;
import com.feng.car.databinding.ActivityVehicleBinding;
import com.feng.car.databinding.ActivityVehicleClassDetailBinding;
import com.feng.car.databinding.ActivityVideoFinalpageBinding;
import com.feng.car.databinding.ActivityViewpointBinding;
import com.feng.car.databinding.ActivityWalletBinding;
import com.feng.car.databinding.ActivityWatchvideoBinding;
import com.feng.car.databinding.ActivityWriteCarInfoBinding;
import com.feng.car.databinding.AllprogramGroupItemBinding;
import com.feng.car.databinding.AllprogramItemBinding;
import com.feng.car.databinding.ArticleBottomInfolineLayoutBinding;
import com.feng.car.databinding.ArticleCommentArraydialogBinding;
import com.feng.car.databinding.ArticleCommentItemLayoutBinding;
import com.feng.car.databinding.ArticleHeaderViewBinding;
import com.feng.car.databinding.ArticleItemEmptyLayoutBinding;
import com.feng.car.databinding.ArticleItemLayoutBinding;
import com.feng.car.databinding.ArticleRecommendItemBinding;
import com.feng.car.databinding.ArticleShareDialogBinding;
import com.feng.car.databinding.AtContentItemBinding;
import com.feng.car.databinding.AtSearchUserHeadLayoutBinding;
import com.feng.car.databinding.AtUserItemLayoutBinding;
import com.feng.car.databinding.AtUserLayoutBinding;
import com.feng.car.databinding.CameraErrorLayoutBinding;
import com.feng.car.databinding.CarComparisonItemBinding;
import com.feng.car.databinding.CarComparisonLayoutBinding;
import com.feng.car.databinding.CarModelListItemBinding;
import com.feng.car.databinding.CarOwnerPriceHeaderBinding;
import com.feng.car.databinding.CarOwnerPriceItemBinding;
import com.feng.car.databinding.CarPhotoListItemBinding;
import com.feng.car.databinding.CarPriceCityHeaderBinding;
import com.feng.car.databinding.CarPriceHeaderCityItemBinding;
import com.feng.car.databinding.CarSeriesPopWinBinding;
import com.feng.car.databinding.CarsOwnerPriceItemBinding;
import com.feng.car.databinding.CarsRelevantItemBinding;
import com.feng.car.databinding.CarxpriceRankingItemBinding;
import com.feng.car.databinding.ChoiceFragmentLayoutBinding;
import com.feng.car.databinding.ChoicenessHeaderLayoutBinding;
import com.feng.car.databinding.ChooseCarDeatailShareDialogBinding;
import com.feng.car.databinding.ChooseCarItemLayoutBinding;
import com.feng.car.databinding.ChooseCarRelevantCarItemBinding;
import com.feng.car.databinding.CircleAccededHeaderBinding;
import com.feng.car.databinding.CircleCarFagmentBinding;
import com.feng.car.databinding.CircleFragmentEmptyLayoutBinding;
import com.feng.car.databinding.CircleGuideHeaderBinding;
import com.feng.car.databinding.CircleRecommendItemBinding;
import com.feng.car.databinding.CircleRecommendLayoutBinding;
import com.feng.car.databinding.CircleSortListArraydialogBinding;
import com.feng.car.databinding.CircleTabHeadLayoutBinding;
import com.feng.car.databinding.CityItemLayoutBinding;
import com.feng.car.databinding.CommentDetailHeaderLayoutBinding;
import com.feng.car.databinding.CommentDetailItemLayoutBinding;
import com.feng.car.databinding.CommentDialogLayoutBinding;
import com.feng.car.databinding.CommodityItemLayoutBinding;
import com.feng.car.databinding.CommoditySelectItemLayoutBinding;
import com.feng.car.databinding.CommonBottomDialogItemBinding;
import com.feng.car.databinding.CommonBottomDialogLayoutBinding;
import com.feng.car.databinding.CommonPostItemLayoutBinding;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.databinding.ConfigClassifyItemLayoutBinding;
import com.feng.car.databinding.ConfigParameterItemLayoutBinding;
import com.feng.car.databinding.CopyPasteLayoutBinding;
import com.feng.car.databinding.DialogClearCacheBinding;
import com.feng.car.databinding.DialogLogoutBinding;
import com.feng.car.databinding.DialogOpenNotifyBinding;
import com.feng.car.databinding.DialogShareScreenShotBinding;
import com.feng.car.databinding.EditarticleApplyDialogBinding;
import com.feng.car.databinding.EmptyLayoutBinding;
import com.feng.car.databinding.EmptyTemplateLayoutBinding;
import com.feng.car.databinding.FindPageFragmentBinding;
import com.feng.car.databinding.FindPageFragmentHeaderBinding;
import com.feng.car.databinding.FollowPageFragmentBinding;
import com.feng.car.databinding.FollowedCircleItemBinding;
import com.feng.car.databinding.ForwardOperationLayoutBinding;
import com.feng.car.databinding.FragmentCarPhotoListBinding;
import com.feng.car.databinding.FragmentMessageBinding;
import com.feng.car.databinding.FragmentMineBinding;
import com.feng.car.databinding.FragmentVehicleContentBinding;
import com.feng.car.databinding.GoodsItemListLayoutBinding;
import com.feng.car.databinding.GpsHeaderLayoutBinding;
import com.feng.car.databinding.HeadMineBinding;
import com.feng.car.databinding.HeaderNewSubjectBinding;
import com.feng.car.databinding.HeaderPersonalSearchBinding;
import com.feng.car.databinding.HomeMainBinding;
import com.feng.car.databinding.HomeOhterFragmentBinding;
import com.feng.car.databinding.HomePageNewFragmentBinding;
import com.feng.car.databinding.HorizonTopicItemBinding;
import com.feng.car.databinding.HotCarseriesItemBinding;
import com.feng.car.databinding.HotshowRemindLayoutBinding;
import com.feng.car.databinding.ItemBlackListBinding;
import com.feng.car.databinding.ItemCarBrandBinding;
import com.feng.car.databinding.ItemCarModelLayoutBinding;
import com.feng.car.databinding.ItemCarModleAddBinding;
import com.feng.car.databinding.ItemCarSeriesBinding;
import com.feng.car.databinding.ItemCircleAccededBinding;
import com.feng.car.databinding.ItemFansBinding;
import com.feng.car.databinding.ItemFindFollowBinding;
import com.feng.car.databinding.ItemHomeMenuLayoutBinding;
import com.feng.car.databinding.ItemHotShowBinding;
import com.feng.car.databinding.ItemImageSelectPhotoBinding;
import com.feng.car.databinding.ItemLavelLayoutBinding;
import com.feng.car.databinding.ItemMicroHeaderUserLayoutBinding;
import com.feng.car.databinding.ItemMineBinding;
import com.feng.car.databinding.ItemMyBoughtRecommendBinding;
import com.feng.car.databinding.ItemRearrangeImageBinding;
import com.feng.car.databinding.ItemShowCarImageViewBinding;
import com.feng.car.databinding.ItemVehicleAgencyBinding;
import com.feng.car.databinding.ItemVehicleChooseGuideLayoutBinding;
import com.feng.car.databinding.ItemVehiclesBinding;
import com.feng.car.databinding.ItemVideoviewBinding;
import com.feng.car.databinding.LavelHeadLayoutBinding;
import com.feng.car.databinding.LayoutMessageHeadBinding;
import com.feng.car.databinding.LayoutPopularProgramListHeaderBinding;
import com.feng.car.databinding.LayoutVehicleDetailTopicItemBinding;
import com.feng.car.databinding.LevelConditionChildLayoutBinding;
import com.feng.car.databinding.LevelConditionItemBinding;
import com.feng.car.databinding.LevelConditionLayoutBinding;
import com.feng.car.databinding.LevelDialogLayoutBinding;
import com.feng.car.databinding.LoginBy5xBinding;
import com.feng.car.databinding.LoginBy5xExplainBinding;
import com.feng.car.databinding.MediaMeasureHeaderLayoutBinding;
import com.feng.car.databinding.MessageCommentItemBinding;
import com.feng.car.databinding.MessageLetterItemBinding;
import com.feng.car.databinding.MineCircleItemBinding;
import com.feng.car.databinding.MsgPraiseItemBinding;
import com.feng.car.databinding.MyFollowedCircleHeaderLayoutBinding;
import com.feng.car.databinding.NetCheckActivityBinding;
import com.feng.car.databinding.NetErrorLayoutBinding;
import com.feng.car.databinding.NewAssociatedSearchItemBinding;
import com.feng.car.databinding.NewSearchAllHeaderBinding;
import com.feng.car.databinding.NewTitleBarLayoutBinding;
import com.feng.car.databinding.OldDriverChooseCarItemBinding;
import com.feng.car.databinding.OpenStoreHeaderBinding;
import com.feng.car.databinding.PermissionSettingDialogBinding;
import com.feng.car.databinding.PersonalHomeHeaderLayoutBinding;
import com.feng.car.databinding.PhotoTextListItemBinding;
import com.feng.car.databinding.PhotoTextUserListItemBinding;
import com.feng.car.databinding.PhotoVideoFolderBinding;
import com.feng.car.databinding.PhotoVideoItemBinding;
import com.feng.car.databinding.PopularProgramListArraydialogBinding;
import com.feng.car.databinding.PopularProgramShareDialogBinding;
import com.feng.car.databinding.PopularShowsHostItemBinding;
import com.feng.car.databinding.PostDragBottomBinding;
import com.feng.car.databinding.PostDragHeadBinding;
import com.feng.car.databinding.PostInitActivityBinding;
import com.feng.car.databinding.PostInitItemBinding;
import com.feng.car.databinding.PostInitKeyboardHeadLayoutBinding;
import com.feng.car.databinding.PraiseMsgActivtyBinding;
import com.feng.car.databinding.PriceDialogLayoutBinding;
import com.feng.car.databinding.PriceFromLayoutBinding;
import com.feng.car.databinding.PriceRankingTagItemBinding;
import com.feng.car.databinding.PriceTipsDialogBinding;
import com.feng.car.databinding.PricesSeriesChartLayoutBinding;
import com.feng.car.databinding.PrivateChatItemBinding;
import com.feng.car.databinding.PrivateLetterItemBinding;
import com.feng.car.databinding.ProgressItemLayoutBinding;
import com.feng.car.databinding.RechargingDialogLayoutBinding;
import com.feng.car.databinding.RechargingLoadingLayoutBinding;
import com.feng.car.databinding.RecommendedTopicItemBinding;
import com.feng.car.databinding.RelativeGoodsItemBinding;
import com.feng.car.databinding.SearchCarItemBinding;
import com.feng.car.databinding.SearchCarModelItemLayoutBinding;
import com.feng.car.databinding.SearchCarsConditionLayoutBinding;
import com.feng.car.databinding.SearchCarsHeadLayoutBinding;
import com.feng.car.databinding.SearchConditionChildItemLayoutBinding;
import com.feng.car.databinding.SearchConditionItemLayoutBinding;
import com.feng.car.databinding.SearchDefaultItemBinding;
import com.feng.car.databinding.SearchPriceCarsLayoutBinding;
import com.feng.car.databinding.SearchcarResultItemBinding;
import com.feng.car.databinding.SearchcarTypegroupItemBinding;
import com.feng.car.databinding.SelectCarByBrandActivity1Binding;
import com.feng.car.databinding.SelectCarByBrandActivityBinding;
import com.feng.car.databinding.SelectCoverItemBinding;
import com.feng.car.databinding.SelectMiddleTitleBinding;
import com.feng.car.databinding.SendStoreMediaLayoutBinding;
import com.feng.car.databinding.SendTranspondBottomMenuBarBinding;
import com.feng.car.databinding.ShareDialogNewBinding;
import com.feng.car.databinding.ShowBigimageViewBinding;
import com.feng.car.databinding.SingleConfigItemLayoutBinding;
import com.feng.car.databinding.SingleConfigureActivityBinding;
import com.feng.car.databinding.SiteNotifictionDetailActivityBinding;
import com.feng.car.databinding.SystemMsgItemBinding;
import com.feng.car.databinding.SystemNoticeActivityBinding;
import com.feng.car.databinding.TabCarFriendsCircleItemLayoutBinding;
import com.feng.car.databinding.TabMenuItemLayoutBinding;
import com.feng.car.databinding.TabVehicleContentItemLayoutBinding;
import com.feng.car.databinding.TabVehicleItemLayoutBinding;
import com.feng.car.databinding.TimepickerLayoutBinding;
import com.feng.car.databinding.UpdateVersionViewBinding;
import com.feng.car.databinding.UploadInvoiceItemBinding;
import com.feng.car.databinding.UploadInvoiceWindowLayoutBinding;
import com.feng.car.databinding.UploadProgressLayoutBinding;
import com.feng.car.databinding.VideoCacheItemBinding;
import com.feng.car.databinding.VideoSelectimageTimelineLayoutBinding;
import com.feng.car.databinding.VideocropTimelineLayoutBinding;
import com.feng.car.databinding.VideofinalpageHeaderBinding;
import com.feng.car.databinding.ViewpointHeaderLayoutBinding;
import com.feng.car.databinding.VoiceChooseCarItemBinding;
import com.feng.car.databinding.WalletDetailActivityBinding;
import com.feng.car.databinding.WalletDetailItemBinding;
import com.feng.car.databinding.WalletDetailTopPopBinding;
import com.feng.car.databinding.WalletWithdrawCashDialogBinding;
import com.feng.car.databinding.WalletWithdrawVerificationDialogBinding;
import com.feng.car.databinding.WebActivityBinding;
import com.feng.car.databinding.WebMoreDialogBinding;
import com.feng.car.databinding.WechatBindActicityBinding;
import com.feng.car.databinding.WriteServeItemLayoutBinding;

class DataBinderMapper {
    static final int TARGET_MIN_SDK = 16;

    private static class InnerBrLookup {
        static String[] sKeys = new String[]{"_all", "bonusDetailInfo", "carBean", "carBrandInfo", "carImageInfo", "carInfo", "carModelInfo", "carSeriesInfo", "carSeriesName", "carmodel", "carseriesname", "carxInfo", "city", "cityInfo", "cityPriceInfo", "commentInfo", "commonurl", UriUtil.LOCAL_CONTENT_SCHEME, "coverimage", "dealerInfo", "description", "edit1", "hash", "height", "hotShowInfo", "id", "imageInfo", "indext", "info", "item", "key", "localVerify", "loginUserInfo", "mHotShowInfo", "mIsShowSel", "mOldDriverVoiceInfo", "mSnsInfo", "markInfo", "messageCountInfo", "messageInfo", "mime", "mobile", "msgInfo", "nType", "openSoftKeyboard", "outsideurl", "position", "price", "realname", "recommendInfo", "saletype", "searchItem", "selectIndex", "selpos", "seriesInfo", "sex", "shopRegisterInfo", "shopaddress", "shopname", "showDescribe", "showIcon", "size", "snsInfo", "softOpen", "sort", "str", "systemInfo", "time", "title", "type", "unWanted", "unuse", "unwanted", "url", "useless", "userInfo", "userless", "videodefinition", "width"};

        private InnerBrLookup() {
        }
    }

    public ViewDataBinding getDataBinder(DataBindingComponent bindingComponent, View view, int layoutId) {
        switch (layoutId) {
            case R.layout.activity_about_us /*2130903067*/:
                return ActivityAboutUsBinding.bind(view, bindingComponent);
            case R.layout.activity_add_subject /*2130903068*/:
                return ActivityAddSubjectBinding.bind(view, bindingComponent);
            case R.layout.activity_add_subject_header /*2130903069*/:
                return ActivityAddSubjectHeaderBinding.bind(view, bindingComponent);
            case R.layout.activity_all_car /*2130903070*/:
                return ActivityAllCarBinding.bind(view, bindingComponent);
            case R.layout.activity_allprogram /*2130903071*/:
                return ActivityAllprogramBinding.bind(view, bindingComponent);
            case R.layout.activity_article_detail /*2130903072*/:
                return ActivityArticleDetailBinding.bind(view, bindingComponent);
            case R.layout.activity_at_me /*2130903073*/:
                return ActivityAtMeBinding.bind(view, bindingComponent);
            case R.layout.activity_audio_play_detail /*2130903074*/:
                return ActivityAudioPlayDetailBinding.bind(view, bindingComponent);
            case R.layout.activity_base_layout /*2130903075*/:
                return ActivityBaseLayoutBinding.bind(view, bindingComponent);
            case R.layout.activity_blacklist /*2130903076*/:
                return ActivityBlacklistBinding.bind(view, bindingComponent);
            case R.layout.activity_camera /*2130903077*/:
                return ActivityCameraBinding.bind(view, bindingComponent);
            case R.layout.activity_camera_preview /*2130903078*/:
                return ActivityCameraPreviewBinding.bind(view, bindingComponent);
            case R.layout.activity_car_model_list /*2130903079*/:
                return ActivityCarModelListBinding.bind(view, bindingComponent);
            case R.layout.activity_car_photo_list /*2130903080*/:
                return ActivityCarPhotoListBinding.bind(view, bindingComponent);
            case R.layout.activity_cars_price_ranking_new /*2130903081*/:
                return ActivityCarsPriceRankingNewBinding.bind(view, bindingComponent);
            case R.layout.activity_circle /*2130903082*/:
                return ActivityCircleBinding.bind(view, bindingComponent);
            case R.layout.activity_city_list /*2130903083*/:
                return ActivityCityListBinding.bind(view, bindingComponent);
            case R.layout.activity_comment /*2130903084*/:
                return ActivityCommentBinding.bind(view, bindingComponent);
            case R.layout.activity_comment_detail /*2130903085*/:
                return ActivityCommentDetailBinding.bind(view, bindingComponent);
            case R.layout.activity_configure_compare /*2130903086*/:
                return ActivityConfigureCompareBinding.bind(view, bindingComponent);
            case R.layout.activity_crop /*2130903087*/:
                return ActivityCropBinding.bind(view, bindingComponent);
            case R.layout.activity_distributor_map /*2130903088*/:
                return ActivityDistributorMapBinding.bind(view, bindingComponent);
            case R.layout.activity_edit_user_info /*2130903089*/:
                return ActivityEditUserInfoBinding.bind(view, bindingComponent);
            case R.layout.activity_edit_user_next /*2130903090*/:
                return ActivityEditUserNextBinding.bind(view, bindingComponent);
            case R.layout.activity_fans /*2130903091*/:
                return ActivityFansBinding.bind(view, bindingComponent);
            case R.layout.activity_feedback /*2130903092*/:
                return ActivityFeedbackBinding.bind(view, bindingComponent);
            case R.layout.activity_find_follow /*2130903093*/:
                return ActivityFindFollowBinding.bind(view, bindingComponent);
            case R.layout.activity_goods_serve_layout /*2130903094*/:
                return ActivityGoodsServeLayoutBinding.bind(view, bindingComponent);
            case R.layout.activity_guide /*2130903095*/:
                return ActivityGuideBinding.bind(view, bindingComponent);
            case R.layout.activity_history /*2130903096*/:
                return ActivityHistoryBinding.bind(view, bindingComponent);
            case R.layout.activity_level_condition /*2130903097*/:
                return ActivityLevelConditionBinding.bind(view, bindingComponent);
            case R.layout.activity_login /*2130903098*/:
                return ActivityLoginBinding.bind(view, bindingComponent);
            case R.layout.activity_main /*2130903099*/:
                return ActivityMainBinding.bind(view, bindingComponent);
            case R.layout.activity_media_measurement_detail /*2130903100*/:
                return ActivityMediaMeasurementDetailBinding.bind(view, bindingComponent);
            case R.layout.activity_new_personal_home /*2130903101*/:
                return ActivityNewPersonalHomeBinding.bind(view, bindingComponent);
            case R.layout.activity_new_search /*2130903102*/:
                return ActivityNewSearchBinding.bind(view, bindingComponent);
            case R.layout.activity_new_subject /*2130903103*/:
                return ActivityNewSubjectBinding.bind(view, bindingComponent);
            case R.layout.activity_open_store /*2130903104*/:
                return ActivityOpenStoreBinding.bind(view, bindingComponent);
            case R.layout.activity_open_store_header /*2130903105*/:
                return ActivityOpenStoreHeaderBinding.bind(view, bindingComponent);
            case R.layout.activity_personal_home_new /*2130903106*/:
                return ActivityPersonalHomeNewBinding.bind(view, bindingComponent);
            case R.layout.activity_personal_search /*2130903107*/:
                return ActivityPersonalSearchBinding.bind(view, bindingComponent);
            case R.layout.activity_popular_program_list /*2130903108*/:
                return ActivityPopularProgramListBinding.bind(view, bindingComponent);
            case R.layout.activity_preview_localimage /*2130903109*/:
                return ActivityPreviewLocalimageBinding.bind(view, bindingComponent);
            case R.layout.activity_price /*2130903110*/:
                return ActivityPriceBinding.bind(view, bindingComponent);
            case R.layout.activity_price_condition /*2130903111*/:
                return ActivityPriceConditionBinding.bind(view, bindingComponent);
            case R.layout.activity_price_new_ranking /*2130903112*/:
                return ActivityPriceNewRankingBinding.bind(view, bindingComponent);
            case R.layout.activity_private_chat /*2130903113*/:
                return ActivityPrivateChatBinding.bind(view, bindingComponent);
            case R.layout.activity_private_letter_list /*2130903114*/:
                return ActivityPrivateLetterListBinding.bind(view, bindingComponent);
            case R.layout.activity_private_setting /*2130903115*/:
                return ActivityPrivateSettingBinding.bind(view, bindingComponent);
            case R.layout.activity_push /*2130903116*/:
                return ActivityPushBinding.bind(view, bindingComponent);
            case R.layout.activity_push_secondary /*2130903117*/:
                return ActivityPushSecondaryBinding.bind(view, bindingComponent);
            case R.layout.activity_qrcod /*2130903118*/:
                return ActivityQrcodBinding.bind(view, bindingComponent);
            case R.layout.activity_rearrange /*2130903119*/:
                return ActivityRearrangeBinding.bind(view, bindingComponent);
            case R.layout.activity_scanning_login /*2130903120*/:
                return ActivityScanningLoginBinding.bind(view, bindingComponent);
            case R.layout.activity_search_car /*2130903121*/:
                return ActivitySearchCarBinding.bind(view, bindingComponent);
            case R.layout.activity_search_city /*2130903122*/:
                return ActivitySearchCityBinding.bind(view, bindingComponent);
            case R.layout.activity_search_condition_layout /*2130903123*/:
                return ActivitySearchConditionLayoutBinding.bind(view, bindingComponent);
            case R.layout.activity_search_related_tag /*2130903124*/:
                return ActivitySearchRelatedTagBinding.bind(view, bindingComponent);
            case R.layout.activity_search_result /*2130903125*/:
                return ActivitySearchResultBinding.bind(view, bindingComponent);
            case R.layout.activity_searchcar_brand /*2130903126*/:
                return ActivitySearchcarBrandBinding.bind(view, bindingComponent);
            case R.layout.activity_searchcar_level /*2130903127*/:
                return ActivitySearchcarLevelBinding.bind(view, bindingComponent);
            case R.layout.activity_searchcar_result /*2130903128*/:
                return ActivitySearchcarResultBinding.bind(view, bindingComponent);
            case R.layout.activity_select_article_cover /*2130903129*/:
                return ActivitySelectArticleCoverBinding.bind(view, bindingComponent);
            case R.layout.activity_select_goods_layout /*2130903130*/:
                return ActivitySelectGoodsLayoutBinding.bind(view, bindingComponent);
            case R.layout.activity_select_photo /*2130903131*/:
                return ActivitySelectPhotoBinding.bind(view, bindingComponent);
            case R.layout.activity_send_comment_new /*2130903132*/:
                return ActivitySendCommentNewBinding.bind(view, bindingComponent);
            case R.layout.activity_send_private /*2130903133*/:
                return ActivitySendPrivateBinding.bind(view, bindingComponent);
            case R.layout.activity_setting /*2130903134*/:
                return ActivitySettingBinding.bind(view, bindingComponent);
            case R.layout.activity_setting_account_phone /*2130903135*/:
                return ActivitySettingAccountPhoneBinding.bind(view, bindingComponent);
            case R.layout.activity_setting_account_security /*2130903136*/:
                return ActivitySettingAccountSecurityBinding.bind(view, bindingComponent);
            case R.layout.activity_shortvideo_crop /*2130903137*/:
                return ActivityShortvideoCropBinding.bind(view, bindingComponent);
            case R.layout.activity_shortvideo_selectimage /*2130903138*/:
                return ActivityShortvideoSelectimageBinding.bind(view, bindingComponent);
            case R.layout.activity_show_bigimage /*2130903139*/:
                return ActivityShowBigimageBinding.bind(view, bindingComponent);
            case R.layout.activity_show_carimage /*2130903140*/:
                return ActivityShowCarimageBinding.bind(view, bindingComponent);
            case R.layout.activity_show_native_image /*2130903141*/:
                return ActivityShowNativeImageBinding.bind(view, bindingComponent);
            case R.layout.activity_splash /*2130903142*/:
                return ActivitySplashBinding.bind(view, bindingComponent);
            case R.layout.activity_upload_idcard /*2130903144*/:
                return ActivityUploadIdcardBinding.bind(view, bindingComponent);
            case R.layout.activity_upload_price_image /*2130903145*/:
                return ActivityUploadPriceImageBinding.bind(view, bindingComponent);
            case R.layout.activity_vehicle /*2130903146*/:
                return ActivityVehicleBinding.bind(view, bindingComponent);
            case R.layout.activity_vehicle_class_detail /*2130903147*/:
                return ActivityVehicleClassDetailBinding.bind(view, bindingComponent);
            case R.layout.activity_video_finalpage /*2130903148*/:
                return ActivityVideoFinalpageBinding.bind(view, bindingComponent);
            case R.layout.activity_viewpoint /*2130903149*/:
                return ActivityViewpointBinding.bind(view, bindingComponent);
            case R.layout.activity_wallet /*2130903150*/:
                return ActivityWalletBinding.bind(view, bindingComponent);
            case R.layout.activity_watchvideo /*2130903151*/:
                return ActivityWatchvideoBinding.bind(view, bindingComponent);
            case R.layout.activity_write_car_info /*2130903152*/:
                return ActivityWriteCarInfoBinding.bind(view, bindingComponent);
            case R.layout.allprogram_group_item /*2130903153*/:
                return AllprogramGroupItemBinding.bind(view, bindingComponent);
            case R.layout.allprogram_item /*2130903154*/:
                return AllprogramItemBinding.bind(view, bindingComponent);
            case R.layout.article_bottom_infoline_layout /*2130903155*/:
                return ArticleBottomInfolineLayoutBinding.bind(view, bindingComponent);
            case R.layout.article_comment_arraydialog /*2130903156*/:
                return ArticleCommentArraydialogBinding.bind(view, bindingComponent);
            case R.layout.article_comment_item_layout /*2130903157*/:
                return ArticleCommentItemLayoutBinding.bind(view, bindingComponent);
            case R.layout.article_header_view /*2130903158*/:
                return ArticleHeaderViewBinding.bind(view, bindingComponent);
            case R.layout.article_item_empty_layout /*2130903159*/:
                return ArticleItemEmptyLayoutBinding.bind(view, bindingComponent);
            case R.layout.article_item_layout /*2130903160*/:
                return ArticleItemLayoutBinding.bind(view, bindingComponent);
            case R.layout.article_recommend_item /*2130903161*/:
                return ArticleRecommendItemBinding.bind(view, bindingComponent);
            case R.layout.article_share_dialog /*2130903162*/:
                return ArticleShareDialogBinding.bind(view, bindingComponent);
            case R.layout.at_content_item /*2130903163*/:
                return AtContentItemBinding.bind(view, bindingComponent);
            case R.layout.at_search_user_head_layout /*2130903164*/:
                return AtSearchUserHeadLayoutBinding.bind(view, bindingComponent);
            case R.layout.at_user_item_layout /*2130903165*/:
                return AtUserItemLayoutBinding.bind(view, bindingComponent);
            case R.layout.at_user_layout /*2130903166*/:
                return AtUserLayoutBinding.bind(view, bindingComponent);
            case R.layout.camera_error_layout /*2130903169*/:
                return CameraErrorLayoutBinding.bind(view, bindingComponent);
            case R.layout.car_comparison_item /*2130903170*/:
                return CarComparisonItemBinding.bind(view, bindingComponent);
            case R.layout.car_comparison_layout /*2130903171*/:
                return CarComparisonLayoutBinding.bind(view, bindingComponent);
            case R.layout.car_model_list_item /*2130903172*/:
                return CarModelListItemBinding.bind(view, bindingComponent);
            case R.layout.car_owner_price_header /*2130903173*/:
                return CarOwnerPriceHeaderBinding.bind(view, bindingComponent);
            case R.layout.car_owner_price_item /*2130903174*/:
                return CarOwnerPriceItemBinding.bind(view, bindingComponent);
            case R.layout.car_photo_list_item /*2130903175*/:
                return CarPhotoListItemBinding.bind(view, bindingComponent);
            case R.layout.car_price_city_header /*2130903176*/:
                return CarPriceCityHeaderBinding.bind(view, bindingComponent);
            case R.layout.car_price_header_city_item /*2130903177*/:
                return CarPriceHeaderCityItemBinding.bind(view, bindingComponent);
            case R.layout.car_series_pop_win /*2130903178*/:
                return CarSeriesPopWinBinding.bind(view, bindingComponent);
            case R.layout.cars_owner_price_item /*2130903179*/:
                return CarsOwnerPriceItemBinding.bind(view, bindingComponent);
            case R.layout.cars_relevant_item /*2130903180*/:
                return CarsRelevantItemBinding.bind(view, bindingComponent);
            case R.layout.carxprice_ranking_item /*2130903181*/:
                return CarxpriceRankingItemBinding.bind(view, bindingComponent);
            case R.layout.choice_fragment_layout /*2130903182*/:
                return ChoiceFragmentLayoutBinding.bind(view, bindingComponent);
            case R.layout.choiceness_header_layout /*2130903183*/:
                return ChoicenessHeaderLayoutBinding.bind(view, bindingComponent);
            case R.layout.choose_car_deatail_share_dialog /*2130903184*/:
                return ChooseCarDeatailShareDialogBinding.bind(view, bindingComponent);
            case R.layout.choose_car_item_layout /*2130903185*/:
                return ChooseCarItemLayoutBinding.bind(view, bindingComponent);
            case R.layout.choose_car_relevant_car_item /*2130903186*/:
                return ChooseCarRelevantCarItemBinding.bind(view, bindingComponent);
            case R.layout.circle_acceded_header /*2130903187*/:
                return CircleAccededHeaderBinding.bind(view, bindingComponent);
            case R.layout.circle_car_fagment /*2130903188*/:
                return CircleCarFagmentBinding.bind(view, bindingComponent);
            case R.layout.circle_fragment_empty_layout /*2130903189*/:
                return CircleFragmentEmptyLayoutBinding.bind(view, bindingComponent);
            case R.layout.circle_guide_header /*2130903190*/:
                return CircleGuideHeaderBinding.bind(view, bindingComponent);
            case R.layout.circle_recommend_item /*2130903191*/:
                return CircleRecommendItemBinding.bind(view, bindingComponent);
            case R.layout.circle_recommend_layout /*2130903192*/:
                return CircleRecommendLayoutBinding.bind(view, bindingComponent);
            case R.layout.circle_sort_list_arraydialog /*2130903193*/:
                return CircleSortListArraydialogBinding.bind(view, bindingComponent);
            case R.layout.circle_tab_head_layout /*2130903194*/:
                return CircleTabHeadLayoutBinding.bind(view, bindingComponent);
            case R.layout.city_item_layout /*2130903195*/:
                return CityItemLayoutBinding.bind(view, bindingComponent);
            case R.layout.comment_detail_header_layout /*2130903196*/:
                return CommentDetailHeaderLayoutBinding.bind(view, bindingComponent);
            case R.layout.comment_detail_item_layout /*2130903197*/:
                return CommentDetailItemLayoutBinding.bind(view, bindingComponent);
            case R.layout.comment_dialog_layout /*2130903198*/:
                return CommentDialogLayoutBinding.bind(view, bindingComponent);
            case R.layout.commodity_item_layout /*2130903199*/:
                return CommodityItemLayoutBinding.bind(view, bindingComponent);
            case R.layout.commodity_select_item_layout /*2130903200*/:
                return CommoditySelectItemLayoutBinding.bind(view, bindingComponent);
            case R.layout.common_bottom_dialog_item /*2130903201*/:
                return CommonBottomDialogItemBinding.bind(view, bindingComponent);
            case R.layout.common_bottom_dialog_layout /*2130903202*/:
                return CommonBottomDialogLayoutBinding.bind(view, bindingComponent);
            case R.layout.common_post_item_layout /*2130903203*/:
                return CommonPostItemLayoutBinding.bind(view, bindingComponent);
            case R.layout.common_recyclerview /*2130903204*/:
                return CommonRecyclerviewBinding.bind(view, bindingComponent);
            case R.layout.config_classify_item_layout /*2130903205*/:
                return ConfigClassifyItemLayoutBinding.bind(view, bindingComponent);
            case R.layout.config_parameter_item_layout /*2130903206*/:
                return ConfigParameterItemLayoutBinding.bind(view, bindingComponent);
            case R.layout.copy_paste_layout /*2130903208*/:
                return CopyPasteLayoutBinding.bind(view, bindingComponent);
            case R.layout.dialog_clear_cache /*2130903223*/:
                return DialogClearCacheBinding.bind(view, bindingComponent);
            case R.layout.dialog_logout /*2130903224*/:
                return DialogLogoutBinding.bind(view, bindingComponent);
            case R.layout.dialog_open_notify /*2130903225*/:
                return DialogOpenNotifyBinding.bind(view, bindingComponent);
            case R.layout.dialog_share_screen_shot /*2130903226*/:
                return DialogShareScreenShotBinding.bind(view, bindingComponent);
            case R.layout.editarticle_apply_dialog /*2130903231*/:
                return EditarticleApplyDialogBinding.bind(view, bindingComponent);
            case R.layout.empty_layout /*2130903239*/:
                return EmptyLayoutBinding.bind(view, bindingComponent);
            case R.layout.empty_template_layout /*2130903240*/:
                return EmptyTemplateLayoutBinding.bind(view, bindingComponent);
            case R.layout.find_page_fragment /*2130903241*/:
                return FindPageFragmentBinding.bind(view, bindingComponent);
            case R.layout.find_page_fragment_header /*2130903242*/:
                return FindPageFragmentHeaderBinding.bind(view, bindingComponent);
            case R.layout.follow_page_fragment /*2130903243*/:
                return FollowPageFragmentBinding.bind(view, bindingComponent);
            case R.layout.followed_circle_item /*2130903244*/:
                return FollowedCircleItemBinding.bind(view, bindingComponent);
            case R.layout.forward_operation_layout /*2130903245*/:
                return ForwardOperationLayoutBinding.bind(view, bindingComponent);
            case R.layout.fragment_car_photo_list /*2130903247*/:
                return FragmentCarPhotoListBinding.bind(view, bindingComponent);
            case R.layout.fragment_message /*2130903248*/:
                return FragmentMessageBinding.bind(view, bindingComponent);
            case R.layout.fragment_mine /*2130903249*/:
                return FragmentMineBinding.bind(view, bindingComponent);
            case R.layout.fragment_vehicle_content /*2130903250*/:
                return FragmentVehicleContentBinding.bind(view, bindingComponent);
            case R.layout.goods_item_list_layout /*2130903251*/:
                return GoodsItemListLayoutBinding.bind(view, bindingComponent);
            case R.layout.gps_header_layout /*2130903252*/:
                return GpsHeaderLayoutBinding.bind(view, bindingComponent);
            case R.layout.head_mine /*2130903253*/:
                return HeadMineBinding.bind(view, bindingComponent);
            case R.layout.header_new_subject /*2130903254*/:
                return HeaderNewSubjectBinding.bind(view, bindingComponent);
            case R.layout.header_personal_search /*2130903255*/:
                return HeaderPersonalSearchBinding.bind(view, bindingComponent);
            case R.layout.home_main /*2130903256*/:
                return HomeMainBinding.bind(view, bindingComponent);
            case R.layout.home_ohter_fragment /*2130903257*/:
                return HomeOhterFragmentBinding.bind(view, bindingComponent);
            case R.layout.home_page_new_fragment /*2130903258*/:
                return HomePageNewFragmentBinding.bind(view, bindingComponent);
            case R.layout.horizon_topic_item /*2130903259*/:
                return HorizonTopicItemBinding.bind(view, bindingComponent);
            case R.layout.hot_carseries_item /*2130903260*/:
                return HotCarseriesItemBinding.bind(view, bindingComponent);
            case R.layout.hotshow_remind_layout /*2130903261*/:
                return HotshowRemindLayoutBinding.bind(view, bindingComponent);
            case R.layout.item_black_list /*2130903263*/:
                return ItemBlackListBinding.bind(view, bindingComponent);
            case R.layout.item_car_brand /*2130903264*/:
                return ItemCarBrandBinding.bind(view, bindingComponent);
            case R.layout.item_car_model_layout /*2130903265*/:
                return ItemCarModelLayoutBinding.bind(view, bindingComponent);
            case R.layout.item_car_modle_add /*2130903266*/:
                return ItemCarModleAddBinding.bind(view, bindingComponent);
            case R.layout.item_car_series /*2130903267*/:
                return ItemCarSeriesBinding.bind(view, bindingComponent);
            case R.layout.item_circle_acceded /*2130903268*/:
                return ItemCircleAccededBinding.bind(view, bindingComponent);
            case R.layout.item_fans /*2130903269*/:
                return ItemFansBinding.bind(view, bindingComponent);
            case R.layout.item_find_follow /*2130903270*/:
                return ItemFindFollowBinding.bind(view, bindingComponent);
            case R.layout.item_home_menu_layout /*2130903271*/:
                return ItemHomeMenuLayoutBinding.bind(view, bindingComponent);
            case R.layout.item_hot_show /*2130903272*/:
                return ItemHotShowBinding.bind(view, bindingComponent);
            case R.layout.item_image_select_photo /*2130903273*/:
                return ItemImageSelectPhotoBinding.bind(view, bindingComponent);
            case R.layout.item_lavel_layout /*2130903274*/:
                return ItemLavelLayoutBinding.bind(view, bindingComponent);
            case R.layout.item_micro_header_user_layout /*2130903275*/:
                return ItemMicroHeaderUserLayoutBinding.bind(view, bindingComponent);
            case R.layout.item_mine /*2130903276*/:
                return ItemMineBinding.bind(view, bindingComponent);
            case R.layout.item_my_bought_recommend /*2130903277*/:
                return ItemMyBoughtRecommendBinding.bind(view, bindingComponent);
            case R.layout.item_rearrange_image /*2130903278*/:
                return ItemRearrangeImageBinding.bind(view, bindingComponent);
            case R.layout.item_show_car_image_view /*2130903280*/:
                return ItemShowCarImageViewBinding.bind(view, bindingComponent);
            case R.layout.item_vehicle_agency /*2130903282*/:
                return ItemVehicleAgencyBinding.bind(view, bindingComponent);
            case R.layout.item_vehicle_choose_guide_layout /*2130903283*/:
                return ItemVehicleChooseGuideLayoutBinding.bind(view, bindingComponent);
            case R.layout.item_vehicles /*2130903284*/:
                return ItemVehiclesBinding.bind(view, bindingComponent);
            case R.layout.item_videoview /*2130903286*/:
                return ItemVideoviewBinding.bind(view, bindingComponent);
            case R.layout.lavel_head_layout /*2130903288*/:
                return LavelHeadLayoutBinding.bind(view, bindingComponent);
            case R.layout.layout_message_head /*2130903290*/:
                return LayoutMessageHeadBinding.bind(view, bindingComponent);
            case R.layout.layout_popular_program_list_header /*2130903291*/:
                return LayoutPopularProgramListHeaderBinding.bind(view, bindingComponent);
            case R.layout.layout_vehicle_detail_topic_item /*2130903299*/:
                return LayoutVehicleDetailTopicItemBinding.bind(view, bindingComponent);
            case R.layout.level_condition_child_layout /*2130903300*/:
                return LevelConditionChildLayoutBinding.bind(view, bindingComponent);
            case R.layout.level_condition_item /*2130903301*/:
                return LevelConditionItemBinding.bind(view, bindingComponent);
            case R.layout.level_condition_layout /*2130903302*/:
                return LevelConditionLayoutBinding.bind(view, bindingComponent);
            case R.layout.level_dialog_layout /*2130903303*/:
                return LevelDialogLayoutBinding.bind(view, bindingComponent);
            case R.layout.login_by_5x /*2130903306*/:
                return LoginBy5xBinding.bind(view, bindingComponent);
            case R.layout.login_by_5x_explain /*2130903307*/:
                return LoginBy5xExplainBinding.bind(view, bindingComponent);
            case R.layout.media_measure_header_layout /*2130903308*/:
                return MediaMeasureHeaderLayoutBinding.bind(view, bindingComponent);
            case R.layout.message_comment_item /*2130903309*/:
                return MessageCommentItemBinding.bind(view, bindingComponent);
            case R.layout.message_letter_item /*2130903310*/:
                return MessageLetterItemBinding.bind(view, bindingComponent);
            case R.layout.mine_circle_item /*2130903312*/:
                return MineCircleItemBinding.bind(view, bindingComponent);
            case R.layout.msg_praise_item /*2130903313*/:
                return MsgPraiseItemBinding.bind(view, bindingComponent);
            case R.layout.my_followed_circle_header_layout /*2130903314*/:
                return MyFollowedCircleHeaderLayoutBinding.bind(view, bindingComponent);
            case R.layout.net_check_activity /*2130903315*/:
                return NetCheckActivityBinding.bind(view, bindingComponent);
            case R.layout.net_error_layout /*2130903316*/:
                return NetErrorLayoutBinding.bind(view, bindingComponent);
            case R.layout.new_associated_search_item /*2130903317*/:
                return NewAssociatedSearchItemBinding.bind(view, bindingComponent);
            case R.layout.new_search_all_header /*2130903318*/:
                return NewSearchAllHeaderBinding.bind(view, bindingComponent);
            case R.layout.new_title_bar_layout /*2130903319*/:
                return NewTitleBarLayoutBinding.bind(view, bindingComponent);
            case R.layout.old_driver_choose_car_item /*2130903335*/:
                return OldDriverChooseCarItemBinding.bind(view, bindingComponent);
            case R.layout.open_store_header /*2130903336*/:
                return OpenStoreHeaderBinding.bind(view, bindingComponent);
            case R.layout.permission_setting_dialog /*2130903337*/:
                return PermissionSettingDialogBinding.bind(view, bindingComponent);
            case R.layout.personal_home_header_layout /*2130903338*/:
                return PersonalHomeHeaderLayoutBinding.bind(view, bindingComponent);
            case R.layout.photo_text_list_item /*2130903339*/:
                return PhotoTextListItemBinding.bind(view, bindingComponent);
            case R.layout.photo_text_user_list_item /*2130903340*/:
                return PhotoTextUserListItemBinding.bind(view, bindingComponent);
            case R.layout.photo_video_folder /*2130903341*/:
                return PhotoVideoFolderBinding.bind(view, bindingComponent);
            case R.layout.photo_video_item /*2130903342*/:
                return PhotoVideoItemBinding.bind(view, bindingComponent);
            case R.layout.popular_program_list_arraydialog /*2130903343*/:
                return PopularProgramListArraydialogBinding.bind(view, bindingComponent);
            case R.layout.popular_program_share_dialog /*2130903344*/:
                return PopularProgramShareDialogBinding.bind(view, bindingComponent);
            case R.layout.popular_shows_host_item /*2130903345*/:
                return PopularShowsHostItemBinding.bind(view, bindingComponent);
            case R.layout.post_drag_bottom /*2130903346*/:
                return PostDragBottomBinding.bind(view, bindingComponent);
            case R.layout.post_drag_head /*2130903347*/:
                return PostDragHeadBinding.bind(view, bindingComponent);
            case R.layout.post_init_activity /*2130903348*/:
                return PostInitActivityBinding.bind(view, bindingComponent);
            case R.layout.post_init_item /*2130903349*/:
                return PostInitItemBinding.bind(view, bindingComponent);
            case R.layout.post_init_keyboard_head_layout /*2130903350*/:
                return PostInitKeyboardHeadLayoutBinding.bind(view, bindingComponent);
            case R.layout.praise_msg_activty /*2130903351*/:
                return PraiseMsgActivtyBinding.bind(view, bindingComponent);
            case R.layout.price_dialog_layout /*2130903352*/:
                return PriceDialogLayoutBinding.bind(view, bindingComponent);
            case R.layout.price_from_layout /*2130903353*/:
                return PriceFromLayoutBinding.bind(view, bindingComponent);
            case R.layout.price_ranking_tag_item /*2130903354*/:
                return PriceRankingTagItemBinding.bind(view, bindingComponent);
            case R.layout.price_tips_dialog /*2130903355*/:
                return PriceTipsDialogBinding.bind(view, bindingComponent);
            case R.layout.prices_series_chart_layout /*2130903356*/:
                return PricesSeriesChartLayoutBinding.bind(view, bindingComponent);
            case R.layout.private_chat_item /*2130903357*/:
                return PrivateChatItemBinding.bind(view, bindingComponent);
            case R.layout.private_letter_item /*2130903358*/:
                return PrivateLetterItemBinding.bind(view, bindingComponent);
            case R.layout.progress_item_layout /*2130903360*/:
                return ProgressItemLayoutBinding.bind(view, bindingComponent);
            case R.layout.recharging_dialog_layout /*2130903366*/:
                return RechargingDialogLayoutBinding.bind(view, bindingComponent);
            case R.layout.recharging_loading_layout /*2130903367*/:
                return RechargingLoadingLayoutBinding.bind(view, bindingComponent);
            case R.layout.recommended_topic_item /*2130903368*/:
                return RecommendedTopicItemBinding.bind(view, bindingComponent);
            case R.layout.relative_goods_item /*2130903369*/:
                return RelativeGoodsItemBinding.bind(view, bindingComponent);
            case R.layout.search_car_item /*2130903370*/:
                return SearchCarItemBinding.bind(view, bindingComponent);
            case R.layout.search_car_model_item_layout /*2130903371*/:
                return SearchCarModelItemLayoutBinding.bind(view, bindingComponent);
            case R.layout.search_cars_condition_layout /*2130903372*/:
                return SearchCarsConditionLayoutBinding.bind(view, bindingComponent);
            case R.layout.search_cars_head_layout /*2130903373*/:
                return SearchCarsHeadLayoutBinding.bind(view, bindingComponent);
            case R.layout.search_condition_child_item_layout /*2130903374*/:
                return SearchConditionChildItemLayoutBinding.bind(view, bindingComponent);
            case R.layout.search_condition_item_layout /*2130903375*/:
                return SearchConditionItemLayoutBinding.bind(view, bindingComponent);
            case R.layout.search_default_item /*2130903376*/:
                return SearchDefaultItemBinding.bind(view, bindingComponent);
            case R.layout.search_price_cars_layout /*2130903377*/:
                return SearchPriceCarsLayoutBinding.bind(view, bindingComponent);
            case R.layout.searchcar_result_item /*2130903378*/:
                return SearchcarResultItemBinding.bind(view, bindingComponent);
            case R.layout.searchcar_typegroup_item /*2130903379*/:
                return SearchcarTypegroupItemBinding.bind(view, bindingComponent);
            case R.layout.select_car_by_brand_activity /*2130903380*/:
                return SelectCarByBrandActivityBinding.bind(view, bindingComponent);
            case R.layout.select_car_by_brand_activity1 /*2130903381*/:
                return SelectCarByBrandActivity1Binding.bind(view, bindingComponent);
            case R.layout.select_cover_item /*2130903382*/:
                return SelectCoverItemBinding.bind(view, bindingComponent);
            case R.layout.select_middle_title /*2130903386*/:
                return SelectMiddleTitleBinding.bind(view, bindingComponent);
            case R.layout.send_store_media_layout /*2130903387*/:
                return SendStoreMediaLayoutBinding.bind(view, bindingComponent);
            case R.layout.send_transpond_bottom_menu_bar /*2130903388*/:
                return SendTranspondBottomMenuBarBinding.bind(view, bindingComponent);
            case R.layout.share_dialog_new /*2130903389*/:
                return ShareDialogNewBinding.bind(view, bindingComponent);
            case R.layout.show_bigimage_view /*2130903390*/:
                return ShowBigimageViewBinding.bind(view, bindingComponent);
            case R.layout.single_config_item_layout /*2130903391*/:
                return SingleConfigItemLayoutBinding.bind(view, bindingComponent);
            case R.layout.single_configure_activity /*2130903392*/:
                return SingleConfigureActivityBinding.bind(view, bindingComponent);
            case R.layout.site_notifiction_detail_activity /*2130903393*/:
                return SiteNotifictionDetailActivityBinding.bind(view, bindingComponent);
            case R.layout.system_msg_item /*2130903405*/:
                return SystemMsgItemBinding.bind(view, bindingComponent);
            case R.layout.system_notice_activity /*2130903406*/:
                return SystemNoticeActivityBinding.bind(view, bindingComponent);
            case R.layout.tab_car_friends_circle_item_layout /*2130903407*/:
                return TabCarFriendsCircleItemLayoutBinding.bind(view, bindingComponent);
            case R.layout.tab_menu_item_layout /*2130903408*/:
                return TabMenuItemLayoutBinding.bind(view, bindingComponent);
            case R.layout.tab_vehicle_content_item_layout /*2130903409*/:
                return TabVehicleContentItemLayoutBinding.bind(view, bindingComponent);
            case R.layout.tab_vehicle_item_layout /*2130903410*/:
                return TabVehicleItemLayoutBinding.bind(view, bindingComponent);
            case R.layout.timepicker_layout /*2130903411*/:
                return TimepickerLayoutBinding.bind(view, bindingComponent);
            case R.layout.update_version_view /*2130903415*/:
                return UpdateVersionViewBinding.bind(view, bindingComponent);
            case R.layout.upload_invoice_item /*2130903416*/:
                return UploadInvoiceItemBinding.bind(view, bindingComponent);
            case R.layout.upload_invoice_window_layout /*2130903417*/:
                return UploadInvoiceWindowLayoutBinding.bind(view, bindingComponent);
            case R.layout.upload_progress_layout /*2130903418*/:
                return UploadProgressLayoutBinding.bind(view, bindingComponent);
            case R.layout.video_cache_item /*2130903423*/:
                return VideoCacheItemBinding.bind(view, bindingComponent);
            case R.layout.video_selectimage_timeline_layout /*2130903424*/:
                return VideoSelectimageTimelineLayoutBinding.bind(view, bindingComponent);
            case R.layout.videocrop_timeline_layout /*2130903425*/:
                return VideocropTimelineLayoutBinding.bind(view, bindingComponent);
            case R.layout.videofinalpage_header /*2130903426*/:
                return VideofinalpageHeaderBinding.bind(view, bindingComponent);
            case R.layout.viewpoint_header_layout /*2130903428*/:
                return ViewpointHeaderLayoutBinding.bind(view, bindingComponent);
            case R.layout.voice_choose_car_item /*2130903429*/:
                return VoiceChooseCarItemBinding.bind(view, bindingComponent);
            case R.layout.wallet_detail_activity /*2130903430*/:
                return WalletDetailActivityBinding.bind(view, bindingComponent);
            case R.layout.wallet_detail_item /*2130903431*/:
                return WalletDetailItemBinding.bind(view, bindingComponent);
            case R.layout.wallet_detail_top_pop /*2130903432*/:
                return WalletDetailTopPopBinding.bind(view, bindingComponent);
            case R.layout.wallet_withdraw_cash_dialog /*2130903433*/:
                return WalletWithdrawCashDialogBinding.bind(view, bindingComponent);
            case R.layout.wallet_withdraw_verification_dialog /*2130903434*/:
                return WalletWithdrawVerificationDialogBinding.bind(view, bindingComponent);
            case R.layout.web_activity /*2130903435*/:
                return WebActivityBinding.bind(view, bindingComponent);
            case R.layout.web_more_dialog /*2130903436*/:
                return WebMoreDialogBinding.bind(view, bindingComponent);
            case R.layout.wechat_bind_acticity /*2130903437*/:
                return WechatBindActicityBinding.bind(view, bindingComponent);
            case R.layout.write_serve_item_layout /*2130903438*/:
                return WriteServeItemLayoutBinding.bind(view, bindingComponent);
            default:
                return null;
        }
    }

    ViewDataBinding getDataBinder(DataBindingComponent bindingComponent, View[] views, int layoutId) {
        return null;
    }

    int getLayoutId(String tag) {
        if (tag == null) {
            return 0;
        }
        switch (tag.hashCode()) {
            case -2142006090:
                if (tag.equals("layout/searchcar_typegroup_item_0")) {
                    return R.layout.searchcar_typegroup_item;
                }
                return 0;
            case -2139120742:
                if (tag.equals("layout/activity_add_subject_header_0")) {
                    return R.layout.activity_add_subject_header;
                }
                return 0;
            case -2125172866:
                if (tag.equals("layout/lavel_head_layout_0")) {
                    return R.layout.lavel_head_layout;
                }
                return 0;
            case -2123085354:
                if (tag.equals("layout/activity_shortvideo_crop_0")) {
                    return R.layout.activity_shortvideo_crop;
                }
                return 0;
            case -2093084994:
                if (tag.equals("layout/net_check_activity_0")) {
                    return R.layout.net_check_activity;
                }
                return 0;
            case -2088861767:
                if (tag.equals("layout/praise_msg_activty_0")) {
                    return R.layout.praise_msg_activty;
                }
                return 0;
            case -2075740360:
                if (tag.equals("layout/city_item_layout_0")) {
                    return R.layout.city_item_layout;
                }
                return 0;
            case -2070381208:
                if (tag.equals("layout/activity_open_store_0")) {
                    return R.layout.activity_open_store;
                }
                return 0;
            case -2062299977:
                if (tag.equals("layout/post_init_keyboard_head_layout_0")) {
                    return R.layout.post_init_keyboard_head_layout;
                }
                return 0;
            case -2058073530:
                if (tag.equals("layout/circle_tab_head_layout_0")) {
                    return R.layout.circle_tab_head_layout;
                }
                return 0;
            case -2053758935:
                if (tag.equals("layout/empty_template_layout_0")) {
                    return R.layout.empty_template_layout;
                }
                return 0;
            case -2027307012:
                if (tag.equals("layout/single_config_item_layout_0")) {
                    return R.layout.single_config_item_layout;
                }
                return 0;
            case -2020316824:
                if (tag.equals("layout/circle_acceded_header_0")) {
                    return R.layout.circle_acceded_header;
                }
                return 0;
            case -1957988463:
                if (tag.equals("layout/activity_viewpoint_0")) {
                    return R.layout.activity_viewpoint;
                }
                return 0;
            case -1931430235:
                if (tag.equals("layout/activity_new_personal_home_0")) {
                    return R.layout.activity_new_personal_home;
                }
                return 0;
            case -1891428742:
                if (tag.equals("layout/item_car_series_0")) {
                    return R.layout.item_car_series;
                }
                return 0;
            case -1888088199:
                if (tag.equals("layout/config_parameter_item_layout_0")) {
                    return R.layout.config_parameter_item_layout;
                }
                return 0;
            case -1882247262:
                if (tag.equals("layout/editarticle_apply_dialog_0")) {
                    return R.layout.editarticle_apply_dialog;
                }
                return 0;
            case -1850574447:
                if (tag.equals("layout/choice_fragment_layout_0")) {
                    return R.layout.choice_fragment_layout;
                }
                return 0;
            case -1807882003:
                if (tag.equals("layout/system_msg_item_0")) {
                    return R.layout.system_msg_item;
                }
                return 0;
            case -1770469028:
                if (tag.equals("layout/private_letter_item_0")) {
                    return R.layout.private_letter_item;
                }
                return 0;
            case -1767501580:
                if (tag.equals("layout/prices_series_chart_layout_0")) {
                    return R.layout.prices_series_chart_layout;
                }
                return 0;
            case -1765549741:
                if (tag.equals("layout/activity_new_subject_0")) {
                    return R.layout.activity_new_subject;
                }
                return 0;
            case -1758409245:
                if (tag.equals("layout/activity_show_bigimage_0")) {
                    return R.layout.activity_show_bigimage;
                }
                return 0;
            case -1752123524:
                if (tag.equals("layout/item_car_modle_add_0")) {
                    return R.layout.item_car_modle_add;
                }
                return 0;
            case -1751916882:
                if (tag.equals("layout/searchcar_result_item_0")) {
                    return R.layout.searchcar_result_item;
                }
                return 0;
            case -1739078076:
                if (tag.equals("layout/msg_praise_item_0")) {
                    return R.layout.msg_praise_item;
                }
                return 0;
            case -1731967285:
                if (tag.equals("layout/activity_search_condition_layout_0")) {
                    return R.layout.activity_search_condition_layout;
                }
                return 0;
            case -1688972616:
                if (tag.equals("layout/item_fans_0")) {
                    return R.layout.item_fans;
                }
                return 0;
            case -1685983731:
                if (tag.equals("layout/search_cars_condition_layout_0")) {
                    return R.layout.search_cars_condition_layout;
                }
                return 0;
            case -1678811786:
                if (tag.equals("layout/choose_car_deatail_share_dialog_0")) {
                    return R.layout.choose_car_deatail_share_dialog;
                }
                return 0;
            case -1589714201:
                if (tag.equals("layout/personal_home_header_layout_0")) {
                    return R.layout.personal_home_header_layout;
                }
                return 0;
            case -1566434666:
                if (tag.equals("layout/tab_menu_item_layout_0")) {
                    return R.layout.tab_menu_item_layout;
                }
                return 0;
            case -1548298357:
                if (tag.equals("layout/article_item_empty_layout_0")) {
                    return R.layout.article_item_empty_layout;
                }
                return 0;
            case -1525384347:
                if (tag.equals("layout/item_my_bought_recommend_0")) {
                    return R.layout.item_my_bought_recommend;
                }
                return 0;
            case -1505946916:
                if (tag.equals("layout/recharging_dialog_layout_0")) {
                    return R.layout.recharging_dialog_layout;
                }
                return 0;
            case -1481193845:
                if (tag.equals("layout/item_mine_0")) {
                    return R.layout.item_mine;
                }
                return 0;
            case -1462582005:
                if (tag.equals("layout/choiceness_header_layout_0")) {
                    return R.layout.choiceness_header_layout;
                }
                return 0;
            case -1400437734:
                if (tag.equals("layout/timepicker_layout_0")) {
                    return R.layout.timepicker_layout;
                }
                return 0;
            case -1399196248:
                if (tag.equals("layout/follow_page_fragment_0")) {
                    return R.layout.follow_page_fragment;
                }
                return 0;
            case -1398886442:
                if (tag.equals("layout/activity_setting_0")) {
                    return R.layout.activity_setting;
                }
                return 0;
            case -1393406685:
                if (tag.equals("layout/activity_new_search_0")) {
                    return R.layout.activity_new_search;
                }
                return 0;
            case -1362053238:
                if (tag.equals("layout/select_cover_item_0")) {
                    return R.layout.select_cover_item;
                }
                return 0;
            case -1351373075:
                if (tag.equals("layout/dialog_logout_0")) {
                    return R.layout.dialog_logout;
                }
                return 0;
            case -1341518043:
                if (tag.equals("layout/home_main_0")) {
                    return R.layout.home_main;
                }
                return 0;
            case -1337811036:
                if (tag.equals("layout/send_transpond_bottom_menu_bar_0")) {
                    return R.layout.send_transpond_bottom_menu_bar;
                }
                return 0;
            case -1330271288:
                if (tag.equals("layout/circle_sort_list_arraydialog_0")) {
                    return R.layout.circle_sort_list_arraydialog;
                }
                return 0;
            case -1325310118:
                if (tag.equals("layout/activity_search_result_0")) {
                    return R.layout.activity_search_result;
                }
                return 0;
            case -1302396916:
                if (tag.equals("layout/activity_searchcar_result_0")) {
                    return R.layout.activity_searchcar_result;
                }
                return 0;
            case -1273969942:
                if (tag.equals("layout/activity_at_me_0")) {
                    return R.layout.activity_at_me;
                }
                return 0;
            case -1273763971:
                if (tag.equals("layout/old_driver_choose_car_item_0")) {
                    return R.layout.old_driver_choose_car_item;
                }
                return 0;
            case -1265568153:
                if (tag.equals("layout/activity_select_article_cover_0")) {
                    return R.layout.activity_select_article_cover;
                }
                return 0;
            case -1247364191:
                if (tag.equals("layout/message_comment_item_0")) {
                    return R.layout.message_comment_item;
                }
                return 0;
            case -1241967214:
                if (tag.equals("layout/level_dialog_layout_0")) {
                    return R.layout.level_dialog_layout;
                }
                return 0;
            case -1236619928:
                if (tag.equals("layout/car_photo_list_item_0")) {
                    return R.layout.car_photo_list_item;
                }
                return 0;
            case -1224725899:
                if (tag.equals("layout/price_from_layout_0")) {
                    return R.layout.price_from_layout;
                }
                return 0;
            case -1197275878:
                if (tag.equals("layout/activity_history_0")) {
                    return R.layout.activity_history;
                }
                return 0;
            case -1186672696:
                if (tag.equals("layout/activity_send_private_0")) {
                    return R.layout.activity_send_private;
                }
                return 0;
            case -1166959191:
                if (tag.equals("layout/activity_edit_user_info_0")) {
                    return R.layout.activity_edit_user_info;
                }
                return 0;
            case -1132023558:
                if (tag.equals("layout/at_user_item_layout_0")) {
                    return R.layout.at_user_item_layout;
                }
                return 0;
            case -1062945935:
                if (tag.equals("layout/photo_text_user_list_item_0")) {
                    return R.layout.photo_text_user_list_item;
                }
                return 0;
            case -1031584082:
                if (tag.equals("layout/activity_edit_user_next_0")) {
                    return R.layout.activity_edit_user_next;
                }
                return 0;
            case -1012156583:
                if (tag.equals("layout/activity_audio_play_detail_0")) {
                    return R.layout.activity_audio_play_detail;
                }
                return 0;
            case -1007496598:
                if (tag.equals("layout/item_videoview_0")) {
                    return R.layout.item_videoview;
                }
                return 0;
            case -978359506:
                if (tag.equals("layout/fragment_mine_0")) {
                    return R.layout.fragment_mine;
                }
                return 0;
            case -971455443:
                if (tag.equals("layout/wallet_detail_activity_0")) {
                    return R.layout.wallet_detail_activity;
                }
                return 0;
            case -949042030:
                if (tag.equals("layout/empty_layout_0")) {
                    return R.layout.empty_layout;
                }
                return 0;
            case -936599263:
                if (tag.equals("layout/activity_camera_0")) {
                    return R.layout.activity_camera;
                }
                return 0;
            case -932968807:
                if (tag.equals("layout/net_error_layout_0")) {
                    return R.layout.net_error_layout;
                }
                return 0;
            case -921142419:
                if (tag.equals("layout/post_drag_bottom_0")) {
                    return R.layout.post_drag_bottom;
                }
                return 0;
            case -910104269:
                if (tag.equals("layout/home_ohter_fragment_0")) {
                    return R.layout.home_ohter_fragment;
                }
                return 0;
            case -907144161:
                if (tag.equals("layout/layout_vehicle_detail_topic_item_0")) {
                    return R.layout.layout_vehicle_detail_topic_item;
                }
                return 0;
            case -906728847:
                if (tag.equals("layout/at_content_item_0")) {
                    return R.layout.at_content_item;
                }
                return 0;
            case -905894432:
                if (tag.equals("layout/item_show_car_image_view_0")) {
                    return R.layout.item_show_car_image_view;
                }
                return 0;
            case -902418009:
                if (tag.equals("layout/activity_price_new_ranking_0")) {
                    return R.layout.activity_price_new_ranking;
                }
                return 0;
            case -897047778:
                if (tag.equals("layout/head_mine_0")) {
                    return R.layout.head_mine;
                }
                return 0;
            case -894637617:
                if (tag.equals("layout/activity_price_0")) {
                    return R.layout.activity_price;
                }
                return 0;
            case -882534854:
                if (tag.equals("layout/photo_video_item_0")) {
                    return R.layout.photo_video_item;
                }
                return 0;
            case -868666925:
                if (tag.equals("layout/gps_header_layout_0")) {
                    return R.layout.gps_header_layout;
                }
                return 0;
            case -866918762:
                if (tag.equals("layout/car_owner_price_item_0")) {
                    return R.layout.car_owner_price_item;
                }
                return 0;
            case -863738629:
                if (tag.equals("layout/hotshow_remind_layout_0")) {
                    return R.layout.hotshow_remind_layout;
                }
                return 0;
            case -859721336:
                if (tag.equals("layout/level_condition_item_0")) {
                    return R.layout.level_condition_item;
                }
                return 0;
            case -848004084:
                if (tag.equals("layout/search_cars_head_layout_0")) {
                    return R.layout.search_cars_head_layout;
                }
                return 0;
            case -842197186:
                if (tag.equals("layout/comment_detail_item_layout_0")) {
                    return R.layout.comment_detail_item_layout;
                }
                return 0;
            case -839550189:
                if (tag.equals("layout/permission_setting_dialog_0")) {
                    return R.layout.permission_setting_dialog;
                }
                return 0;
            case -820833445:
                if (tag.equals("layout/commodity_select_item_layout_0")) {
                    return R.layout.commodity_select_item_layout;
                }
                return 0;
            case -814262527:
                if (tag.equals("layout/car_comparison_layout_0")) {
                    return R.layout.car_comparison_layout;
                }
                return 0;
            case -811436815:
                if (tag.equals("layout/wallet_withdraw_cash_dialog_0")) {
                    return R.layout.wallet_withdraw_cash_dialog;
                }
                return 0;
            case -803670753:
                if (tag.equals("layout/tab_vehicle_content_item_layout_0")) {
                    return R.layout.tab_vehicle_content_item_layout;
                }
                return 0;
            case -797312483:
                if (tag.equals("layout/dialog_open_notify_0")) {
                    return R.layout.dialog_open_notify;
                }
                return 0;
            case -758140355:
                if (tag.equals("layout/activity_find_follow_0")) {
                    return R.layout.activity_find_follow;
                }
                return 0;
            case -723475074:
                if (tag.equals("layout/goods_item_list_layout_0")) {
                    return R.layout.goods_item_list_layout;
                }
                return 0;
            case -717758027:
                if (tag.equals("layout/post_init_activity_0")) {
                    return R.layout.post_init_activity;
                }
                return 0;
            case -704518297:
                if (tag.equals("layout/item_hot_show_0")) {
                    return R.layout.item_hot_show;
                }
                return 0;
            case -686447744:
                if (tag.equals("layout/videocrop_timeline_layout_0")) {
                    return R.layout.videocrop_timeline_layout;
                }
                return 0;
            case -671955293:
                if (tag.equals("layout/activity_preview_localimage_0")) {
                    return R.layout.activity_preview_localimage;
                }
                return 0;
            case -665815518:
                if (tag.equals("layout/post_drag_head_0")) {
                    return R.layout.post_drag_head;
                }
                return 0;
            case -625009863:
                if (tag.equals("layout/post_init_item_0")) {
                    return R.layout.post_init_item;
                }
                return 0;
            case -612770862:
                if (tag.equals("layout/write_serve_item_layout_0")) {
                    return R.layout.write_serve_item_layout;
                }
                return 0;
            case -611948110:
                if (tag.equals("layout/activity_vehicle_0")) {
                    return R.layout.activity_vehicle;
                }
                return 0;
            case -603446149:
                if (tag.equals("layout/relative_goods_item_0")) {
                    return R.layout.relative_goods_item;
                }
                return 0;
            case -590037165:
                if (tag.equals("layout/web_more_dialog_0")) {
                    return R.layout.web_more_dialog;
                }
                return 0;
            case -580128176:
                if (tag.equals("layout/web_activity_0")) {
                    return R.layout.web_activity;
                }
                return 0;
            case -544103820:
                if (tag.equals("layout/search_car_model_item_layout_0")) {
                    return R.layout.search_car_model_item_layout;
                }
                return 0;
            case -505545874:
                if (tag.equals("layout/update_version_view_0")) {
                    return R.layout.update_version_view;
                }
                return 0;
            case -472086716:
                if (tag.equals("layout/comment_detail_header_layout_0")) {
                    return R.layout.comment_detail_header_layout;
                }
                return 0;
            case -462664459:
                if (tag.equals("layout/activity_wallet_0")) {
                    return R.layout.activity_wallet;
                }
                return 0;
            case -429820277:
                if (tag.equals("layout/cars_relevant_item_0")) {
                    return R.layout.cars_relevant_item;
                }
                return 0;
            case -425830514:
                if (tag.equals("layout/layout_popular_program_list_header_0")) {
                    return R.layout.layout_popular_program_list_header;
                }
                return 0;
            case -423538617:
                if (tag.equals("layout/popular_shows_host_item_0")) {
                    return R.layout.popular_shows_host_item;
                }
                return 0;
            case -384934254:
                if (tag.equals("layout/activity_car_photo_list_0")) {
                    return R.layout.activity_car_photo_list;
                }
                return 0;
            case -377819121:
                if (tag.equals("layout/new_search_all_header_0")) {
                    return R.layout.new_search_all_header;
                }
                return 0;
            case -317873501:
                if (tag.equals("layout/activity_upload_price_image_0")) {
                    return R.layout.activity_upload_price_image;
                }
                return 0;
            case -287336708:
                if (tag.equals("layout/tab_car_friends_circle_item_layout_0")) {
                    return R.layout.tab_car_friends_circle_item_layout;
                }
                return 0;
            case -286390730:
                if (tag.equals("layout/common_bottom_dialog_item_0")) {
                    return R.layout.common_bottom_dialog_item;
                }
                return 0;
            case -265269289:
                if (tag.equals("layout/activity_scanning_login_0")) {
                    return R.layout.activity_scanning_login;
                }
                return 0;
            case -257339882:
                if (tag.equals("layout/at_search_user_head_layout_0")) {
                    return R.layout.at_search_user_head_layout;
                }
                return 0;
            case -250115256:
                if (tag.equals("layout/item_circle_acceded_0")) {
                    return R.layout.item_circle_acceded;
                }
                return 0;
            case -237979845:
                if (tag.equals("layout/activity_show_native_image_0")) {
                    return R.layout.activity_show_native_image;
                }
                return 0;
            case -237232145:
                if (tag.equals("layout/activity_login_0")) {
                    return R.layout.activity_login;
                }
                return 0;
            case -225371335:
                if (tag.equals("layout/activity_search_car_0")) {
                    return R.layout.activity_search_car;
                }
                return 0;
            case -206318910:
                if (tag.equals("layout/activity_guide_0")) {
                    return R.layout.activity_guide;
                }
                return 0;
            case -201331488:
                if (tag.equals("layout/message_letter_item_0")) {
                    return R.layout.message_letter_item;
                }
                return 0;
            case -191379937:
                if (tag.equals("layout/popular_program_share_dialog_0")) {
                    return R.layout.popular_program_share_dialog;
                }
                return 0;
            case -189282761:
                if (tag.equals("layout/popular_program_list_arraydialog_0")) {
                    return R.layout.popular_program_list_arraydialog;
                }
                return 0;
            case -186734018:
                if (tag.equals("layout/find_page_fragment_header_0")) {
                    return R.layout.find_page_fragment_header;
                }
                return 0;
            case -141455728:
                if (tag.equals("layout/commodity_item_layout_0")) {
                    return R.layout.commodity_item_layout;
                }
                return 0;
            case -130021237:
                if (tag.equals("layout/header_personal_search_0")) {
                    return R.layout.header_personal_search;
                }
                return 0;
            case -122828763:
                if (tag.equals("layout/activity_comment_0")) {
                    return R.layout.activity_comment;
                }
                return 0;
            case -91017593:
                if (tag.equals("layout/activity_video_finalpage_0")) {
                    return R.layout.activity_video_finalpage;
                }
                return 0;
            case -90326671:
                if (tag.equals("layout/fragment_car_photo_list_0")) {
                    return R.layout.fragment_car_photo_list;
                }
                return 0;
            case -85095000:
                if (tag.equals("layout/circle_recommend_layout_0")) {
                    return R.layout.circle_recommend_layout;
                }
                return 0;
            case -62685998:
                if (tag.equals("layout/activity_configure_compare_0")) {
                    return R.layout.activity_configure_compare;
                }
                return 0;
            case -61242115:
                if (tag.equals("layout/horizon_topic_item_0")) {
                    return R.layout.horizon_topic_item;
                }
                return 0;
            case -45984996:
                if (tag.equals("layout/activity_select_goods_layout_0")) {
                    return R.layout.activity_select_goods_layout;
                }
                return 0;
            case -41091312:
                if (tag.equals("layout/video_selectimage_timeline_layout_0")) {
                    return R.layout.video_selectimage_timeline_layout;
                }
                return 0;
            case -33595395:
                if (tag.equals("layout/activity_cars_price_ranking_new_0")) {
                    return R.layout.activity_cars_price_ranking_new;
                }
                return 0;
            case -28533657:
                if (tag.equals("layout/activity_vehicle_class_detail_0")) {
                    return R.layout.activity_vehicle_class_detail;
                }
                return 0;
            case -12318531:
                if (tag.equals("layout/activity_qrcod_0")) {
                    return R.layout.activity_qrcod;
                }
                return 0;
            case -2706873:
                if (tag.equals("layout/article_share_dialog_0")) {
                    return R.layout.article_share_dialog;
                }
                return 0;
            case 17079458:
                if (tag.equals("layout/item_home_menu_layout_0")) {
                    return R.layout.item_home_menu_layout;
                }
                return 0;
            case 18852991:
                if (tag.equals("layout/item_rearrange_image_0")) {
                    return R.layout.item_rearrange_image;
                }
                return 0;
            case 41513217:
                if (tag.equals("layout/cars_owner_price_item_0")) {
                    return R.layout.cars_owner_price_item;
                }
                return 0;
            case 54966771:
                if (tag.equals("layout/search_price_cars_layout_0")) {
                    return R.layout.search_price_cars_layout;
                }
                return 0;
            case 72891754:
                if (tag.equals("layout/activity_camera_preview_0")) {
                    return R.layout.activity_camera_preview;
                }
                return 0;
            case 108304491:
                if (tag.equals("layout/activity_select_photo_0")) {
                    return R.layout.activity_select_photo;
                }
                return 0;
            case 135658736:
                if (tag.equals("layout/find_page_fragment_0")) {
                    return R.layout.find_page_fragment;
                }
                return 0;
            case 138350066:
                if (tag.equals("layout/circle_fragment_empty_layout_0")) {
                    return R.layout.circle_fragment_empty_layout;
                }
                return 0;
            case 138732908:
                if (tag.equals("layout/item_vehicle_choose_guide_layout_0")) {
                    return R.layout.item_vehicle_choose_guide_layout;
                }
                return 0;
            case 153342092:
                if (tag.equals("layout/activity_crop_0")) {
                    return R.layout.activity_crop;
                }
                return 0;
            case 160174825:
                if (tag.equals("layout/followed_circle_item_0")) {
                    return R.layout.followed_circle_item;
                }
                return 0;
            case 165036556:
                if (tag.equals("layout/car_price_city_header_0")) {
                    return R.layout.car_price_city_header;
                }
                return 0;
            case 169423558:
                if (tag.equals("layout/activity_open_store_header_0")) {
                    return R.layout.activity_open_store_header;
                }
                return 0;
            case 171161894:
                if (tag.equals("layout/item_car_brand_0")) {
                    return R.layout.item_car_brand;
                }
                return 0;
            case 176849405:
                if (tag.equals("layout/show_bigimage_view_0")) {
                    return R.layout.show_bigimage_view;
                }
                return 0;
            case 177209669:
                if (tag.equals("layout/price_tips_dialog_0")) {
                    return R.layout.price_tips_dialog;
                }
                return 0;
            case 184368900:
                if (tag.equals("layout/config_classify_item_layout_0")) {
                    return R.layout.config_classify_item_layout;
                }
                return 0;
            case 190099423:
                if (tag.equals("layout/car_model_list_item_0")) {
                    return R.layout.car_model_list_item;
                }
                return 0;
            case 223502780:
                if (tag.equals("layout/activity_fans_0")) {
                    return R.layout.activity_fans;
                }
                return 0;
            case 223977005:
                if (tag.equals("layout/activity_comment_detail_0")) {
                    return R.layout.activity_comment_detail;
                }
                return 0;
            case 224914881:
                if (tag.equals("layout/item_find_follow_0")) {
                    return R.layout.item_find_follow;
                }
                return 0;
            case 238844609:
                if (tag.equals("layout/activity_feedback_0")) {
                    return R.layout.activity_feedback;
                }
                return 0;
            case 267208085:
                if (tag.equals("layout/home_page_new_fragment_0")) {
                    return R.layout.home_page_new_fragment;
                }
                return 0;
            case 267433695:
                if (tag.equals("layout/level_condition_layout_0")) {
                    return R.layout.level_condition_layout;
                }
                return 0;
            case 287480741:
                if (tag.equals("layout/photo_text_list_item_0")) {
                    return R.layout.photo_text_list_item;
                }
                return 0;
            case 302602205:
                if (tag.equals("layout/select_middle_title_0")) {
                    return R.layout.select_middle_title;
                }
                return 0;
            case 310358793:
                if (tag.equals("layout/wallet_withdraw_verification_dialog_0")) {
                    return R.layout.wallet_withdraw_verification_dialog;
                }
                return 0;
            case 310617179:
                if (tag.equals("layout/allprogram_item_0")) {
                    return R.layout.allprogram_item;
                }
                return 0;
            case 315698935:
                if (tag.equals("layout/activity_searchcar_level_0")) {
                    return R.layout.activity_searchcar_level;
                }
                return 0;
            case 340049803:
                if (tag.equals("layout/item_image_select_photo_0")) {
                    return R.layout.item_image_select_photo;
                }
                return 0;
            case 365538995:
                if (tag.equals("layout/activity_setting_account_phone_0")) {
                    return R.layout.activity_setting_account_phone;
                }
                return 0;
            case 383642170:
                if (tag.equals("layout/activity_searchcar_brand_0")) {
                    return R.layout.activity_searchcar_brand;
                }
                return 0;
            case 412726617:
                if (tag.equals("layout/layout_message_head_0")) {
                    return R.layout.layout_message_head;
                }
                return 0;
            case 414584876:
                if (tag.equals("layout/copy_paste_layout_0")) {
                    return R.layout.copy_paste_layout;
                }
                return 0;
            case 423753077:
                if (tag.equals("layout/activity_main_0")) {
                    return R.layout.activity_main;
                }
                return 0;
            case 429127620:
                if (tag.equals("layout/article_comment_arraydialog_0")) {
                    return R.layout.article_comment_arraydialog;
                }
                return 0;
            case 440031738:
                if (tag.equals("layout/progress_item_layout_0")) {
                    return R.layout.progress_item_layout;
                }
                return 0;
            case 489488404:
                if (tag.equals("layout/search_default_item_0")) {
                    return R.layout.search_default_item;
                }
                return 0;
            case 514062769:
                if (tag.equals("layout/wallet_detail_item_0")) {
                    return R.layout.wallet_detail_item;
                }
                return 0;
            case 528403094:
                if (tag.equals("layout/activity_push_0")) {
                    return R.layout.activity_push;
                }
                return 0;
            case 542503466:
                if (tag.equals("layout/car_comparison_item_0")) {
                    return R.layout.car_comparison_item;
                }
                return 0;
            case 544711723:
                if (tag.equals("layout/single_configure_activity_0")) {
                    return R.layout.single_configure_activity;
                }
                return 0;
            case 554712562:
                if (tag.equals("layout/forward_operation_layout_0")) {
                    return R.layout.forward_operation_layout;
                }
                return 0;
            case 581714425:
                if (tag.equals("layout/choose_car_item_layout_0")) {
                    return R.layout.choose_car_item_layout;
                }
                return 0;
            case 603769388:
                if (tag.equals("layout/car_price_header_city_item_0")) {
                    return R.layout.car_price_header_city_item;
                }
                return 0;
            case 616032495:
                if (tag.equals("layout/hot_carseries_item_0")) {
                    return R.layout.hot_carseries_item;
                }
                return 0;
            case 631323481:
                if (tag.equals("layout/article_item_layout_0")) {
                    return R.layout.article_item_layout;
                }
                return 0;
            case 669708396:
                if (tag.equals("layout/mine_circle_item_0")) {
                    return R.layout.mine_circle_item;
                }
                return 0;
            case 673217109:
                if (tag.equals("layout/article_recommend_item_0")) {
                    return R.layout.article_recommend_item;
                }
                return 0;
            case 743652296:
                if (tag.equals("layout/activity_watchvideo_0")) {
                    return R.layout.activity_watchvideo;
                }
                return 0;
            case 760419675:
                if (tag.equals("layout/activity_popular_program_list_0")) {
                    return R.layout.activity_popular_program_list;
                }
                return 0;
            case 761247447:
                if (tag.equals("layout/common_post_item_layout_0")) {
                    return R.layout.common_post_item_layout;
                }
                return 0;
            case 782273503:
                if (tag.equals("layout/my_followed_circle_header_layout_0")) {
                    return R.layout.my_followed_circle_header_layout;
                }
                return 0;
            case 787983115:
                if (tag.equals("layout/circle_guide_header_0")) {
                    return R.layout.circle_guide_header;
                }
                return 0;
            case 840397579:
                if (tag.equals("layout/activity_push_secondary_0")) {
                    return R.layout.activity_push_secondary;
                }
                return 0;
            case 848779147:
                if (tag.equals("layout/wallet_detail_top_pop_0")) {
                    return R.layout.wallet_detail_top_pop;
                }
                return 0;
            case 854161273:
                if (tag.equals("layout/article_comment_item_layout_0")) {
                    return R.layout.article_comment_item_layout;
                }
                return 0;
            case 882441153:
                if (tag.equals("layout/login_by_5x_0")) {
                    return R.layout.login_by_5x;
                }
                return 0;
            case 910203332:
                if (tag.equals("layout/common_recyclerview_0")) {
                    return R.layout.common_recyclerview;
                }
                return 0;
            case 913635754:
                if (tag.equals("layout/private_chat_item_0")) {
                    return R.layout.private_chat_item;
                }
                return 0;
            case 918378531:
                if (tag.equals("layout/choose_car_relevant_car_item_0")) {
                    return R.layout.choose_car_relevant_car_item;
                }
                return 0;
            case 919989295:
                if (tag.equals("layout/upload_invoice_item_0")) {
                    return R.layout.upload_invoice_item;
                }
                return 0;
            case 1000619257:
                if (tag.equals("layout/item_lavel_layout_0")) {
                    return R.layout.item_lavel_layout;
                }
                return 0;
            case 1018757388:
                if (tag.equals("layout/activity_about_us_0")) {
                    return R.layout.activity_about_us;
                }
                return 0;
            case 1022261153:
                if (tag.equals("layout/activity_private_letter_list_0")) {
                    return R.layout.activity_private_letter_list;
                }
                return 0;
            case 1078232968:
                if (tag.equals("layout/at_user_layout_0")) {
                    return R.layout.at_user_layout;
                }
                return 0;
            case 1087209579:
                if (tag.equals("layout/activity_search_related_tag_0")) {
                    return R.layout.activity_search_related_tag;
                }
                return 0;
            case 1089784614:
                if (tag.equals("layout/send_store_media_layout_0")) {
                    return R.layout.send_store_media_layout;
                }
                return 0;
            case 1095907702:
                if (tag.equals("layout/activity_article_detail_0")) {
                    return R.layout.activity_article_detail;
                }
                return 0;
            case 1096732711:
                if (tag.equals("layout/car_series_pop_win_0")) {
                    return R.layout.car_series_pop_win;
                }
                return 0;
            case 1119763882:
                if (tag.equals("layout/video_cache_item_0")) {
                    return R.layout.video_cache_item;
                }
                return 0;
            case 1129007481:
                if (tag.equals("layout/login_by_5x_explain_0")) {
                    return R.layout.login_by_5x_explain;
                }
                return 0;
            case 1135223133:
                if (tag.equals("layout/activity_setting_account_security_0")) {
                    return R.layout.activity_setting_account_security;
                }
                return 0;
            case 1136553087:
                if (tag.equals("layout/item_micro_header_user_layout_0")) {
                    return R.layout.item_micro_header_user_layout;
                }
                return 0;
            case 1142180496:
                if (tag.equals("layout/activity_private_chat_0")) {
                    return R.layout.activity_private_chat;
                }
                return 0;
            case 1167190481:
                if (tag.equals("layout/search_condition_item_layout_0")) {
                    return R.layout.search_condition_item_layout;
                }
                return 0;
            case 1168375229:
                if (tag.equals("layout/recommended_topic_item_0")) {
                    return R.layout.recommended_topic_item;
                }
                return 0;
            case 1220930226:
                if (tag.equals("layout/article_bottom_infoline_layout_0")) {
                    return R.layout.article_bottom_infoline_layout;
                }
                return 0;
            case 1231529617:
                if (tag.equals("layout/circle_recommend_item_0")) {
                    return R.layout.circle_recommend_item;
                }
                return 0;
            case 1241852451:
                if (tag.equals("layout/activity_blacklist_0")) {
                    return R.layout.activity_blacklist;
                }
                return 0;
            case 1268068412:
                if (tag.equals("layout/activity_all_car_0")) {
                    return R.layout.activity_all_car;
                }
                return 0;
            case 1279101409:
                if (tag.equals("layout/wechat_bind_acticity_0")) {
                    return R.layout.wechat_bind_acticity;
                }
                return 0;
            case 1282100459:
                if (tag.equals("layout/activity_price_condition_0")) {
                    return R.layout.activity_price_condition;
                }
                return 0;
            case 1299680231:
                if (tag.equals("layout/camera_error_layout_0")) {
                    return R.layout.camera_error_layout;
                }
                return 0;
            case 1301068949:
                if (tag.equals("layout/activity_write_car_info_0")) {
                    return R.layout.activity_write_car_info;
                }
                return 0;
            case 1319590447:
                if (tag.equals("layout/activity_upload_idcard_0")) {
                    return R.layout.activity_upload_idcard;
                }
                return 0;
            case 1327169357:
                if (tag.equals("layout/activity_personal_search_0")) {
                    return R.layout.activity_personal_search;
                }
                return 0;
            case 1338523786:
                if (tag.equals("layout/activity_distributor_map_0")) {
                    return R.layout.activity_distributor_map;
                }
                return 0;
            case 1340813713:
                if (tag.equals("layout/header_new_subject_0")) {
                    return R.layout.header_new_subject;
                }
                return 0;
            case 1350273535:
                if (tag.equals("layout/upload_invoice_window_layout_0")) {
                    return R.layout.upload_invoice_window_layout;
                }
                return 0;
            case 1383006114:
                if (tag.equals("layout/level_condition_child_layout_0")) {
                    return R.layout.level_condition_child_layout;
                }
                return 0;
            case 1391888146:
                if (tag.equals("layout/new_title_bar_layout_0")) {
                    return R.layout.new_title_bar_layout;
                }
                return 0;
            case 1395170906:
                if (tag.equals("layout/article_header_view_0")) {
                    return R.layout.article_header_view;
                }
                return 0;
            case 1403852629:
                if (tag.equals("layout/photo_video_folder_0")) {
                    return R.layout.photo_video_folder;
                }
                return 0;
            case 1424081996:
                if (tag.equals("layout/carxprice_ranking_item_0")) {
                    return R.layout.carxprice_ranking_item;
                }
                return 0;
            case 1466554612:
                if (tag.equals("layout/viewpoint_header_layout_0")) {
                    return R.layout.viewpoint_header_layout;
                }
                return 0;
            case 1482332173:
                if (tag.equals("layout/common_bottom_dialog_layout_0")) {
                    return R.layout.common_bottom_dialog_layout;
                }
                return 0;
            case 1493377374:
                if (tag.equals("layout/activity_base_layout_0")) {
                    return R.layout.activity_base_layout;
                }
                return 0;
            case 1505665486:
                if (tag.equals("layout/fragment_message_0")) {
                    return R.layout.fragment_message;
                }
                return 0;
            case 1508232813:
                if (tag.equals("layout/fragment_vehicle_content_0")) {
                    return R.layout.fragment_vehicle_content;
                }
                return 0;
            case 1523920780:
                if (tag.equals("layout/dialog_share_screen_shot_0")) {
                    return R.layout.dialog_share_screen_shot;
                }
                return 0;
            case 1529869345:
                if (tag.equals("layout/search_car_item_0")) {
                    return R.layout.search_car_item;
                }
                return 0;
            case 1546434859:
                if (tag.equals("layout/activity_media_measurement_detail_0")) {
                    return R.layout.activity_media_measurement_detail;
                }
                return 0;
            case 1573928931:
                if (tag.equals("layout/activity_splash_0")) {
                    return R.layout.activity_splash;
                }
                return 0;
            case 1578274926:
                if (tag.equals("layout/search_condition_child_item_layout_0")) {
                    return R.layout.search_condition_child_item_layout;
                }
                return 0;
            case 1597722140:
                if (tag.equals("layout/select_car_by_brand_activity1_0")) {
                    return R.layout.select_car_by_brand_activity1;
                }
                return 0;
            case 1610855567:
                if (tag.equals("layout/activity_show_carimage_0")) {
                    return R.layout.activity_show_carimage;
                }
                return 0;
            case 1610897448:
                if (tag.equals("layout/activity_search_city_0")) {
                    return R.layout.activity_search_city;
                }
                return 0;
            case 1615263775:
                if (tag.equals("layout/activity_rearrange_0")) {
                    return R.layout.activity_rearrange;
                }
                return 0;
            case 1631557787:
                if (tag.equals("layout/activity_shortvideo_selectimage_0")) {
                    return R.layout.activity_shortvideo_selectimage;
                }
                return 0;
            case 1667423110:
                if (tag.equals("layout/new_associated_search_item_0")) {
                    return R.layout.new_associated_search_item;
                }
                return 0;
            case 1674672820:
                if (tag.equals("layout/upload_progress_layout_0")) {
                    return R.layout.upload_progress_layout;
                }
                return 0;
            case 1701944084:
                if (tag.equals("layout/activity_add_subject_0")) {
                    return R.layout.activity_add_subject;
                }
                return 0;
            case 1706916820:
                if (tag.equals("layout/recharging_loading_layout_0")) {
                    return R.layout.recharging_loading_layout;
                }
                return 0;
            case 1743433407:
                if (tag.equals("layout/activity_allprogram_0")) {
                    return R.layout.activity_allprogram;
                }
                return 0;
            case 1743866437:
                if (tag.equals("layout/voice_choose_car_item_0")) {
                    return R.layout.voice_choose_car_item;
                }
                return 0;
            case 1748954341:
                if (tag.equals("layout/activity_personal_home_new_0")) {
                    return R.layout.activity_personal_home_new;
                }
                return 0;
            case 1785467203:
                if (tag.equals("layout/item_car_model_layout_0")) {
                    return R.layout.item_car_model_layout;
                }
                return 0;
            case 1798056399:
                if (tag.equals("layout/dialog_clear_cache_0")) {
                    return R.layout.dialog_clear_cache;
                }
                return 0;
            case 1814890413:
                if (tag.equals("layout/comment_dialog_layout_0")) {
                    return R.layout.comment_dialog_layout;
                }
                return 0;
            case 1852656119:
                if (tag.equals("layout/select_car_by_brand_activity_0")) {
                    return R.layout.select_car_by_brand_activity;
                }
                return 0;
            case 1853513904:
                if (tag.equals("layout/item_vehicle_agency_0")) {
                    return R.layout.item_vehicle_agency;
                }
                return 0;
            case 1859172006:
                if (tag.equals("layout/activity_level_condition_0")) {
                    return R.layout.activity_level_condition;
                }
                return 0;
            case 1869268165:
                if (tag.equals("layout/activity_send_comment_new_0")) {
                    return R.layout.activity_send_comment_new;
                }
                return 0;
            case 1874158362:
                if (tag.equals("layout/activity_private_setting_0")) {
                    return R.layout.activity_private_setting;
                }
                return 0;
            case 1922340845:
                if (tag.equals("layout/price_ranking_tag_item_0")) {
                    return R.layout.price_ranking_tag_item;
                }
                return 0;
            case 1945365708:
                if (tag.equals("layout/open_store_header_0")) {
                    return R.layout.open_store_header;
                }
                return 0;
            case 1951424876:
                if (tag.equals("layout/media_measure_header_layout_0")) {
                    return R.layout.media_measure_header_layout;
                }
                return 0;
            case 1954115790:
                if (tag.equals("layout/videofinalpage_header_0")) {
                    return R.layout.videofinalpage_header;
                }
                return 0;
            case 1974524600:
                if (tag.equals("layout/activity_city_list_0")) {
                    return R.layout.activity_city_list;
                }
                return 0;
            case 1976956924:
                if (tag.equals("layout/system_notice_activity_0")) {
                    return R.layout.system_notice_activity;
                }
                return 0;
            case 1990473242:
                if (tag.equals("layout/site_notifiction_detail_activity_0")) {
                    return R.layout.site_notifiction_detail_activity;
                }
                return 0;
            case 2009586700:
                if (tag.equals("layout/activity_circle_0")) {
                    return R.layout.activity_circle;
                }
                return 0;
            case 2025649253:
                if (tag.equals("layout/tab_vehicle_item_layout_0")) {
                    return R.layout.tab_vehicle_item_layout;
                }
                return 0;
            case 2031253247:
                if (tag.equals("layout/share_dialog_new_0")) {
                    return R.layout.share_dialog_new;
                }
                return 0;
            case 2044514523:
                if (tag.equals("layout/allprogram_group_item_0")) {
                    return R.layout.allprogram_group_item;
                }
                return 0;
            case 2046594438:
                if (tag.equals("layout/circle_car_fagment_0")) {
                    return R.layout.circle_car_fagment;
                }
                return 0;
            case 2082799959:
                if (tag.equals("layout/price_dialog_layout_0")) {
                    return R.layout.price_dialog_layout;
                }
                return 0;
            case 2104257979:
                if (tag.equals("layout/activity_car_model_list_0")) {
                    return R.layout.activity_car_model_list;
                }
                return 0;
            case 2116645648:
                if (tag.equals("layout/car_owner_price_header_0")) {
                    return R.layout.car_owner_price_header;
                }
                return 0;
            case 2125475967:
                if (tag.equals("layout/item_vehicles_0")) {
                    return R.layout.item_vehicles;
                }
                return 0;
            case 2125886111:
                if (tag.equals("layout/activity_goods_serve_layout_0")) {
                    return R.layout.activity_goods_serve_layout;
                }
                return 0;
            case 2129713942:
                if (tag.equals("layout/item_black_list_0")) {
                    return R.layout.item_black_list;
                }
                return 0;
            default:
                return 0;
        }
    }

    String convertBrIdToString(int id) {
        if (id < 0 || id >= InnerBrLookup.sKeys.length) {
            return null;
        }
        return InnerBrLookup.sKeys[id];
    }
}
