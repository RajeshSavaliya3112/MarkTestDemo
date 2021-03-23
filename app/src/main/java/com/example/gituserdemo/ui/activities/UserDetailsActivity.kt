package com.example.gituserdemo.ui.activities

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.gituserdemo.R
import com.example.gituserdemo.databinding.ActivityUserDetailsBinding
import com.example.gituserdemo.models.UserModel
import com.example.gituserdemo.mvvm.viewmodels.UserDetailsViewmodel
import java.lang.StringBuilder

class UserDetailsActivity : AppCompatActivity() {

    private val TAG = "UserDetailsActivity"
    private var mActivity: UserDetailsActivity? = null
    private val mUserDetailsViewmodel: UserDetailsViewmodel by lazy {
        ViewModelProviders.of(this).get(UserDetailsViewmodel::class.java)
    }
    private val mBinding: ActivityUserDetailsBinding by lazy {
        ActivityUserDetailsBinding.inflate(
            layoutInflater
        )
    }
    private var mProgressDialog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mActivity = this
        initData()
        setUpViewModel()
    }

    private fun initData() {
        mProgressDialog = ProgressDialog(mActivity)
        mProgressDialog?.setCancelable(false)
        mUserDetailsViewmodel.mActivity = mActivity
        mBinding.userIvBack.setOnClickListener { onBackPressed() }

    }

    private fun setUpViewModel() {

        mUserDetailsViewmodel.onProgress.observe(this, Observer {
            if (it == null || it.isBlank()) {
                mProgressDialog?.dismiss()
            } else {
                mProgressDialog?.setMessage(it)
                mProgressDialog?.show()
            }
        })

        mUserDetailsViewmodel.onError.observe(this, Observer {
            if (it == "HTTP 403 ") {
                mBinding.userDetailScroll.visibility = View.GONE
                mBinding.mainError.visibility = View.VISIBLE
                mBinding.mainIvError.visibility = View.VISIBLE
                mBinding.mainTvError.text = getString(R.string.limit_exceeded)
            } else if (it == "No internet") {
                mBinding.userDetailScroll.visibility = View.GONE
                mBinding.mainError.visibility = View.VISIBLE
                mBinding.mainIvError.setImageResource(R.drawable.ic_no_signal)
                mBinding.mainTvError.text = "No internet connection.\n Please try again letter."
            } else
                Toast.makeText(mActivity, "$it", Toast.LENGTH_SHORT).show()
        })


        mUserDetailsViewmodel.userDetailModel.observe(this, Observer {
            setUpModel(it)
        })

        mUserDetailsViewmodel.getUserList(intent.getStringExtra("name") ?: "")

    }


    private fun setUpModel(it: UserModel?) {

        mBinding.apply {
            Glide.with(mActivity!!).load(it?.avatarUrl).placeholder(R.drawable.ic_github_header)
                .into(userIvProfile)
            userTvName.text = it?.name.toString() ?: it?.login
            userTvFollowers.text = "Followers: ${it?.followers}"
            userTvFollowing.text = "Following: ${it?.following}"
            userTvBio.text = getUserBio(it)
        }

    }


    private fun getUserBio(it: UserModel?): String {
        val strBuilder = StringBuilder()
        strBuilder.append("Name: ${it?.name ?: it?.login}").append("\n")
        strBuilder.append("Company: ${it?.company}").append("\n")
        strBuilder.append("Blog: ${it?.blog}").append("\n")
        strBuilder.append("Type: ${it?.type}").append("\n")
        strBuilder.append("Email: ${it?.email}").append("\n")
        strBuilder.append("Twitter handle: ${it?.twitterUsername}").append("\n")
        return strBuilder.toString()
    }


}