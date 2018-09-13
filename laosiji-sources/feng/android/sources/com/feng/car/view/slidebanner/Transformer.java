package com.feng.car.view.slidebanner;

import android.support.v4.view.ViewPager.PageTransformer;
import com.feng.car.view.slidebanner.transformer.AccordionTransformer;
import com.feng.car.view.slidebanner.transformer.BackgroundToForegroundTransformer;
import com.feng.car.view.slidebanner.transformer.CubeInTransformer;
import com.feng.car.view.slidebanner.transformer.CubeOutTransformer;
import com.feng.car.view.slidebanner.transformer.DefaultTransformer;
import com.feng.car.view.slidebanner.transformer.DepthPageTransformer;
import com.feng.car.view.slidebanner.transformer.FlipHorizontalTransformer;
import com.feng.car.view.slidebanner.transformer.FlipVerticalTransformer;
import com.feng.car.view.slidebanner.transformer.ForegroundToBackgroundTransformer;
import com.feng.car.view.slidebanner.transformer.RotateDownTransformer;
import com.feng.car.view.slidebanner.transformer.RotateUpTransformer;
import com.feng.car.view.slidebanner.transformer.ScaleInOutTransformer;
import com.feng.car.view.slidebanner.transformer.StackTransformer;
import com.feng.car.view.slidebanner.transformer.TabletTransformer;
import com.feng.car.view.slidebanner.transformer.ZoomInTransformer;
import com.feng.car.view.slidebanner.transformer.ZoomOutSlideTransformer;
import com.feng.car.view.slidebanner.transformer.ZoomOutTranformer;

public class Transformer {
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
