package com.google.zxing.maxicode.decoder;

import com.feng.car.utils.FengConstant;
import com.feng.car.view.largeimage.BlockImageLoader;
import com.google.zxing.common.BitMatrix;
import com.meizu.cloud.pushsdk.handler.impl.AbstractMessageHandler;
import com.taobao.accs.ErrorCode;
import com.taobao.accs.antibrush.AntiBrush;
import com.taobao.accs.common.Constants;
import com.taobao.accs.flowcontrol.FlowControl;
import com.tencent.ijk.media.player.IMediaPlayer;
import com.tencent.ijk.media.player.IjkMediaMeta;
import com.tencent.rtmp.TXLiveConstants;
import com.umeng.commonsdk.stateless.d;
import com.umeng.socialize.bean.HandlerRequestCode;

final class BitMatrixParser {
    private static final int[][] BITNR = new int[][]{new int[]{121, 120, 127, 126, 133, 132, 139, 138, 145, IjkMediaMeta.FF_PROFILE_H264_HIGH_444, 151, 150, 157, 156, 163, 162, 169, 168, 175, 174, 181, TXLiveConstants.RENDER_ROTATION_180, 187, 186, 193, 192, 199, 198, -2, -2}, new int[]{123, IjkMediaMeta.FF_PROFILE_H264_HIGH_422, 129, 128, 135, 134, 141, 140, 147, 146, 153, 152, 159, 158, 165, 164, 171, 170, 177, 176, 183, 182, 189, 188, 195, 194, Constants.COMMAND_PING, 200, 816, -3}, new int[]{125, 124, 131, 130, 137, 136, 143, 142, 149, 148, 155, 154, 161, 160, 167, 166, 173, 172, 179, 178, 185, 184, 191, 190, 197, 196, 203, 202, 818, 817}, new int[]{283, 282, 277, 276, 271, 270, 265, 264, 259, 258, 253, 252, 247, 246, 241, 240, 235, 234, 229, 228, 223, 222, 217, 216, 211, 210, 205, 204, 819, -3}, new int[]{285, 284, 279, 278, d.a, 272, 267, FengConstant.SINGLE_IMAGE_MIN_WIDTH, 261, 260, 255, 254, 249, 248, 243, 242, 237, 236, 231, 230, 225, 224, 219, 218, 213, 212, 207, 206, 821, 820}, new int[]{287, 286, 281, 280, 275, 274, 269, 268, 263, 262, 257, AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS, 251, FengConstant.IMAGE_SMALL_WIDTH, 245, IjkMediaMeta.FF_PROFILE_H264_HIGH_444_PREDICTIVE, 239, 238, 233, 232, 227, 226, Constants.SDK_VERSION_CODE, 220, 215, 214, 209, 208, 822, -3}, new int[]{289, 288, 295, 294, Constants.COMMAND_STOP_FOR_ELECTION, 300, 307, 306, 313, 312, 319, 318, 325, 324, 331, 330, 337, 336, 343, 342, 349, 348, 355, 354, 361, 360, 367, 366, 824, 823}, new int[]{291, 290, 297, 296, ErrorCode.DM_APPKEY_INVALID, ErrorCode.DM_DEVICEID_INVALID, 309, 308, 315, 314, 321, 320, 327, 326, 333, 332, 339, 338, 345, 344, 351, FengConstant.IMAGEMIDDLEWIDTH_350, 357, 356, 363, 362, 369, 368, 825, -3}, new int[]{293, 292, 299, 298, 305, ErrorCode.DM_PACKAGENAME_INVALID, 311, 310, 317, 316, 323, 322, 329, 328, 335, 334, 341, 340, 347, 346, 353, 352, 359, 358, 365, 364, 371, 370, 827, 826}, new int[]{409, 408, 403, 402, 397, 396, 391, 390, 79, 78, -2, -2, 13, 12, 37, 36, 2, -1, 44, 43, 109, 108, 385, 384, 379, 378, 373, 372, 828, -3}, new int[]{411, 410, 405, 404, 399, 398, 393, 392, 81, 80, 40, -2, 15, 14, 39, 38, 3, -1, -1, 45, 111, 110, 387, 386, 381, 380, 375, 374, 830, 829}, new int[]{413, 412, 407, 406, Constants.COMMAND_GET_VERSION, FengConstant.SINGLE_IMAGE_MAX_WIDTH, 395, 394, 83, 82, 41, -3, -3, -3, -3, -3, 5, 4, 47, 46, 113, 112, 389, 388, 383, 382, 377, 376, 831, -3}, new int[]{415, 414, FlowControl.STATUS_FLOW_CTRL_CUR, FlowControl.STATUS_FLOW_CTRL_ALL, 427, 426, 103, 102, 55, 54, 16, -3, -3, -3, -3, -3, -3, -3, 20, 19, 85, 84, 433, 432, 439, 438, 445, 444, 833, 832}, new int[]{417, 416, 423, FlowControl.STATUS_FLOW_CTRL_BRUSH, 429, 428, 105, 104, 57, 56, -3, -3, -3, -3, -3, -3, -3, -3, 22, 21, 87, 86, 435, 434, 441, 440, 447, 446, 834, -3}, new int[]{AntiBrush.STATUS_BRUSH, 418, 425, 424, 431, 430, 107, 106, 59, 58, -3, -3, -3, -3, -3, -3, -3, -3, -3, 23, 89, 88, 437, 436, Constants.PORT, 442, 449, 448, 836, 835}, new int[]{481, 480, 475, 474, 469, 468, 48, -2, 30, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, 0, 53, 52, 463, 462, 457, 456, 451, 450, 837, -3}, new int[]{483, 482, 477, 476, 471, 470, 49, -1, -2, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -2, -1, 465, 464, 459, 458, 453, 452, 839, 838}, new int[]{485, 484, 479, 478, 473, 472, 51, 50, 31, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, 1, -2, 42, 467, 466, 461, 460, 455, 454, 840, -3}, new int[]{487, 486, 493, 492, 499, 498, 97, 96, 61, 60, -3, -3, -3, -3, -3, -3, -3, -3, -3, 26, 91, 90, 505, 504, 511, 510, 517, 516, 842, 841}, new int[]{489, 488, 495, 494, 501, 500, 99, 98, 63, 62, -3, -3, -3, -3, -3, -3, -3, -3, 28, 27, 93, 92, 507, 506, 513, 512, 519, 518, 843, -3}, new int[]{491, 490, 497, 496, 503, 502, 101, 100, 65, 64, 17, -3, -3, -3, -3, -3, -3, -3, 18, 29, 95, 94, 509, 508, 515, 514, 521, 520, 845, 844}, new int[]{559, 558, 553, 552, 547, 546, 541, 540, 73, 72, 32, -3, -3, -3, -3, -3, -3, 10, 67, 66, 115, 114, 535, 534, 529, 528, 523, 522, 846, -3}, new int[]{561, 560, 555, 554, 549, 548, 543, 542, 75, 74, -2, -1, 7, 6, 35, 34, 11, -2, 69, 68, 117, 116, 537, 536, 531, 530, 525, 524, 848, 847}, new int[]{563, 562, 557, 556, 551, 550, 545, 544, 77, 76, -2, 33, 9, 8, 25, 24, -1, -2, 71, 70, 119, 118, 539, 538, 533, 532, 527, 526, 849, -3}, new int[]{565, 564, 571, 570, 577, 576, 583, 582, 589, 588, 595, 594, 601, 600, 607, 606, 613, 612, 619, 618, 625, 624, 631, 630, 637, 636, 643, 642, 851, 850}, new int[]{567, 566, 573, 572, 579, IjkMediaMeta.FF_PROFILE_H264_CONSTRAINED_BASELINE, 585, 584, 591, 590, 597, 596, 603, 602, 609, 608, 615, 614, 621, 620, 627, 626, 633, 632, 639, 638, 645, 644, 852, -3}, new int[]{569, 568, 575, 574, 581, 580, 587, 586, 593, 592, 599, 598, 605, 604, 611, 610, 617, 616, 623, 622, 629, 628, 635, 634, 641, FengConstant.IMAGEMIDDLEWIDTH, 647, 646, 854, 853}, new int[]{727, 726, 721, 720, 715, 714, 709, 708, IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH, IMediaPlayer.MEDIA_INFO_BUFFERING_END, 697, 696, 691, 690, 685, 684, 679, 678, 673, 672, 667, BlockImageLoader.MESSAGE_LOAD, 661, 660, 655, 654, 649, 648, 855, -3}, new int[]{729, 728, 723, 722, 717, 716, 711, 710, 705, 704, 699, 698, 693, 692, 687, 686, 681, 680, 675, 674, 669, 668, 663, 662, 657, 656, 651, 650, 857, 856}, new int[]{731, 730, 725, 724, 719, 718, 713, 712, 707, 706, IMediaPlayer.MEDIA_INFO_BUFFERING_START, 700, 695, 694, 689, 688, 683, 682, 677, 676, 671, 670, BlockImageLoader.MESSAGE_PIC, 664, 659, 658, 653, 652, 858, -3}, new int[]{733, 732, 739, 738, 745, 744, 751, 750, 757, 756, 763, 762, 769, 768, 775, 774, 781, 780, 787, 786, 793, 792, 799, 798, 805, 804, 811, 810, 860, 859}, new int[]{735, 734, 741, 740, 747, 746, 753, 752, 759, 758, HandlerRequestCode.SINA_SHARE_REQUEST_CODE, 764, 771, 770, 777, 776, 783, 782, 789, 788, 795, 794, IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE, 800, 807, 806, 813, 812, 861, -3}, new int[]{737, 736, 743, 742, 749, 748, 755, 754, 761, 760, 767, 766, 773, 772, 779, 778, 785, 784, 791, 790, 797, 796, 803, IMediaPlayer.MEDIA_INFO_METADATA_UPDATE, 809, 808, 815, 814, 863, 862}};
    private final BitMatrix bitMatrix;

    BitMatrixParser(BitMatrix bitMatrix) {
        this.bitMatrix = bitMatrix;
    }

    byte[] readCodewords() {
        byte[] result = new byte[IjkMediaMeta.FF_PROFILE_H264_HIGH_444];
        int height = this.bitMatrix.getHeight();
        int width = this.bitMatrix.getWidth();
        int y = 0;
        while (y < height) {
            int[] bitnrRow = BITNR[y];
            int x = 0;
            while (x < width) {
                int bit = bitnrRow[x];
                if (bit >= 0 && this.bitMatrix.get(x, y)) {
                    int i = bit / 6;
                    result[i] = (byte) (result[i] | ((byte) (1 << (5 - (bit % 6)))));
                }
                x++;
            }
            y++;
        }
        return result;
    }
}
