package com.ahao.camera;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;

public class AspectRatio implements Comparable<AspectRatio>, Parcelable {
    private final static SparseArrayCompat<SparseArrayCompat<AspectRatio>> sCache
            = new SparseArrayCompat<>(16);

    private final int mX;
    private final int mY;

    public AspectRatio(int mX, int mY) {
        this.mX = mX;
        this.mY = mY;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    public static AspectRatio of(int width, int height) {
        int gcd = gcd(width, height);

        int x = width / gcd;
        int y = height / gcd;

        SparseArrayCompat<AspectRatio> arrayX = sCache.get(x);

        if (arrayX == null) {
            arrayX = new SparseArrayCompat<>();
            AspectRatio aspectRatio = new AspectRatio(x, y);
            arrayX.put(y, aspectRatio);
            sCache.put(x, arrayX);
            return aspectRatio;
        } else {
            AspectRatio aspectRatio = arrayX.get(y);
            if (aspectRatio == null) {
                aspectRatio = new AspectRatio(x, y);
                arrayX.put(y, aspectRatio);
            }
            return aspectRatio;
        }
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int c = b;
            b = a % b;
            a = c;
        }
        return a;
    }

    public boolean matches(CameraSize size) {
        int sizeWidth = size.getWidth();
        int sizeHeight = size.getHeight();
        int gcd = gcd(sizeWidth, sizeHeight);
        int x = sizeWidth / gcd;
        int y = sizeHeight / gcd;
        return mX == x && mY == y;
    }

    /**
     * 将其表现成浮点数
     *
     * @return 浮点数
     */
    public float toFloat() {
        return (float) mX / mY;
    }

    /**
     * 取反
     *
     * @return AspectRatio
     */
    public AspectRatio inverse() {
        return new AspectRatio(mY, mX);
    }

    @Override
    public String toString() {
        return "AspectRatio{" +
                "mX=" + mX +
                ", mY=" + mY +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AspectRatio that = (AspectRatio) o;

        if (mX != that.mX) return false;
        return mY == that.mY;
    }

    @Override
    public int hashCode() {
        int result = mX;
        result = 31 * result + mY;
        return result;
    }

    @Override
    public int compareTo(@NonNull AspectRatio another) {
        if (equals(another)) {
            return 0;
        } else if (toFloat() - another.toFloat() > 0) {
            return 1;
        } else {
            return -1;
        }
    }


    /*************************************************
     Parcelable的实现
     **************************************************/

    public static final Creator<AspectRatio> CREATOR = new Creator<AspectRatio>() {
        @Override
        public AspectRatio createFromParcel(Parcel in) {
            return new AspectRatio(in);
        }

        @Override
        public AspectRatio[] newArray(int size) {
            return new AspectRatio[size];
        }
    };

    protected AspectRatio(Parcel in) {
        mX = in.readInt();
        mY = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mX);
        dest.writeInt(mY);
    }

}
