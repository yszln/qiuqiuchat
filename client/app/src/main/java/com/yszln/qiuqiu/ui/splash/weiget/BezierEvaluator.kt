package com.yszln.qiuqiu.ui.splash.weiget

import android.animation.TypeEvaluator
import android.graphics.PointF

/**
 * @fileName: BezierEvaluator
 * @author: huwei
 * @date: 2020/6/9 16:41
 * @description: 贝塞尔估值器
 * @history:
 */
class BezierEvaluator : TypeEvaluator<PointF> {

    private var mPoint1: PointF;
    private var mPoint2: PointF;

    constructor(mPoint1: PointF, mPoint2: PointF) {
        this.mPoint1 = mPoint1
        this.mPoint2 = mPoint2
    }


    override fun evaluate(t: Float, point0: PointF, point3: PointF): PointF {
        val currentPoint = PointF()
        //计算横坐标
        currentPoint.x =
            point0.x * (1 - t) * (1 - t) * (1 - t) +
                    3 * mPoint1.x * t*(1 - t) * (1 - t) +
                    3 * mPoint2.x * t * t * (1 - t) +
                    point3.x * t * t * t;
        //计算纵坐标
        currentPoint.y =
            point0.y * (1 - t) * (1 - t) * (1 - t) +
                    3 * mPoint1.y * t* (1 - t) * (1 - t) +
                    3 * mPoint2.y * t * t * (1 - t) +
                    point3.y * t * t * t;
        return currentPoint;
    }
}