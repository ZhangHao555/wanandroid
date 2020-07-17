package com.ahao.camera;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class CameraSize implements Comparable<CameraSize>{
    private final int mWidth;
    private final int mHeight;

    public CameraSize(int mWidth, int mHeight) {
        this.mWidth = mWidth;
        this.mHeight = mHeight;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public CameraSize inverse(){
        return new CameraSize(mHeight,mWidth);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CameraSize that = (CameraSize) o;
        return mWidth == that.mWidth &&
                mHeight == that.mHeight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mWidth, mHeight);
    }

    @Override
    public String toString() {
        return "CameraSize{" +
                "mWidth=" + mWidth +
                ", mHeight=" + mHeight +
                '}';
    }

    @Override
    public int compareTo(CameraSize o) {
        return this.mHeight * this.mWidth - o.mHeight * o.mWidth;
    }

    public static class ISizeMap {
        private final HashMap<AspectRatio, SortedSet<CameraSize>> mRatioSizeSets = new HashMap<>();

        public boolean add(CameraSize size) {
            for (AspectRatio aspectRatio : mRatioSizeSets.keySet()) {
                if (aspectRatio.matches(size)) {
                    SortedSet<CameraSize> cameraSizes = mRatioSizeSets.get(aspectRatio);
                    if (cameraSizes.contains(size)) {
                        return false;
                    } else {
                        cameraSizes.add(size);
                        return true;
                    }
                }
            }

            //没有找到当前的尺寸的话
            SortedSet<CameraSize> sizes = new TreeSet<>();
            sizes.add(size);
            mRatioSizeSets.put(AspectRatio.of(size.getWidth(), size.getHeight()), sizes);
            return true;
        }

        public void remove(AspectRatio ratio) {
            mRatioSizeSets.remove(ratio);
        }

        public Set<AspectRatio> ratios() {
            return mRatioSizeSets.keySet();
        }

        public SortedSet<CameraSize> sizes(AspectRatio ratio) {
            return mRatioSizeSets.get(ratio);
        }

        public void clear() {
            mRatioSizeSets.clear();
        }

        public boolean isEmpty() {
            return mRatioSizeSets.isEmpty();
        }
    }
}
