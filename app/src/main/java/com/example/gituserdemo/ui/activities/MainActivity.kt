package com.example.gituserdemo.ui.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gituserdemo.R
import com.example.gituserdemo.databinding.ActivityMainBinding
import com.example.gituserdemo.mvvm.viewmodels.MainActivityViewModel
import com.example.gituserdemo.ui.UserListAdapter

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivityViewModel"
    private lateinit var mActivity : MainActivity
    private var mProgressDialog : ProgressDialog? = null
    private var mAdapter : UserListAdapter? = null

    private val mActivityBinding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mMainViewModel : MainActivityViewModel by lazy { ViewModelProviders.of(this).get(MainActivityViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mActivityBinding.root)
        mActivity = this
        initData()
        setUpViewModel()
    }


    private fun initData() {
        mMainViewModel.mActivity = mActivity

        mProgressDialog = ProgressDialog(mActivity)
        mProgressDialog?.setCancelable(false)
        mAdapter = UserListAdapter(mActivity,{
            it.let { it1 -> mMainViewModel.getUserList(it1) }
            mActivityBinding.mainRvUsers.smoothScrollToPosition(
                mAdapter?.userList?.size?.plus(1)!!
            )
        },{
            startActivity(Intent(mActivity,UserDetailsActivity::class.java).apply {
                putExtra("name", it)
            })
        })

        mActivityBinding.mainRvUsers.layoutManager = LinearLayoutManager(mActivity)
        mActivityBinding.mainRvUsers.adapter = mAdapter

        mActivityBinding.mainEtSearch.addTextChangedListener {
            mActivityBinding.mainIvBack.visibility = View.VISIBLE
            mAdapter?.filterText(it.toString())
            mAdapter?.isSearching = it.toString().isNotEmpty()
        }

        mActivityBinding.mainIvBack.setOnClickListener {
            mActivityBinding.mainEtSearch.setText("")
            mAdapter?.isSearching = false
            mActivityBinding.mainIvBack.visibility = View.GONE
        }

    }


    private fun setUpViewModel() {

        mMainViewModel.onProgress.observe(this, Observer {
            if(it == null){
                mProgressDialog?.dismiss()
            } else{
                mProgressDialog?.setMessage("$it")
                mProgressDialog?.show()
            }
        })


        mMainViewModel.onError.observe(this, Observer {
            Log.e(TAG, "setUpViewModel: error -->$it" )
            if( it == "HTTP 403 "){
                mActivityBinding.mainRvUsers.visibility = View.GONE
                mActivityBinding.mainError.visibility = View.VISIBLE
                mActivityBinding.mainIvError.visibility = View.VISIBLE
                mActivityBinding.mainTvError.text = getString(R.string.limit_exceeded)
            } else if(it == "No internet") {

                if(mAdapter?.userList?.isEmpty() == true){
                    mActivityBinding.mainRvUsers.visibility = View.GONE
                    mActivityBinding.mainError.visibility = View.VISIBLE
                    mActivityBinding.mainIvError.setImageResource(R.drawable.ic_no_signal)
                    mActivityBinding.mainTvError.text = "No internet connection.\n Please try again letter."
                } else {
                    Toast.makeText(mActivity, "No internet connection. Please try again letter.", Toast.LENGTH_SHORT).show()
                }

            } else
                Toast.makeText(mActivity, "$it", Toast.LENGTH_SHORT).show()
        })


        mMainViewModel.userDataList.observe(this, Observer {
            Log.e(TAG, "setUpViewModel: user size --> ${it.size}" )
            mAdapter?.isLoading = false
            mAdapter?.setData(it)
        })

        mMainViewModel.getUserList(0)

    }




}