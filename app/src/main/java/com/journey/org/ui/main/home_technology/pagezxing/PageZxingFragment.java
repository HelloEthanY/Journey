package com.journey.org.ui.main.home_technology.pagezxing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.databinding.FragmentPageZxingBinding;
import com.yzq.zxinglibrary.encode.CodeCreator;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * 二维码 - zxing
 *
 * @author 逍遥
 * @Date 2019/12/10
 */
public class PageZxingFragment extends BaseFragment<FragmentPageZxingBinding, PageZxingViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_page_zxing;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        binding.btnGenerateQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                 * contentEtString：字符串内容
                 * w：图片的宽
                 * h：图片的高
                 * logo：不需要logo的话直接传null
                 * */

                String content = "";
                content = binding.editorContent.getText().toString().trim();
                if ("".equals(content)) {
                    content = "http://www.baidu.com";
                }
                Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                Bitmap bitmap = CodeCreator.createQRCode(content, 400, 400, logo);
                binding.ivShowQr.setImageBitmap(bitmap);
            }
        });
    }
}
