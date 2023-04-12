package com.example.nycschools.home.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nycschools.R
import com.example.nycschools.home.data.PostModel
import com.example.nycschools.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HomeAdapter.HomeListener {

    @Inject
    private lateinit var vm:HomeViewModel
    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm = ViewModelProvider(this)[HomeViewModel::class.java]

        initAdapter()

        vm.fetchAllPosts()

        vm.postModelListLiveData?.observe(this, Observer {
            if (it!=null){
                rv_home.visibility = View.VISIBLE
                adapter.setData(it as ArrayList<PostModel>)
            }else{
                showToast("Something went wrong")
            }
            progress_home.visibility = View.GONE
        })

    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.home_menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.menu_create_post -> showCreatePOstDialog()
//        }
//        return true
//    }

//    private fun showCreatePOstDialog() {
//        val dialog = Dialog(this)
//        val view = LayoutInflater.from(this).inflate(R.layout.create_post_dialog, null)
//        dialog.setContentView(view)
//
//        var title = ""
//        var body = ""
//
//        view.btn_submit.setOnClickListener {
//            title = view.et_title.text.toString().trim()
//            body = view.et_body.text.toString().trim()
//
//            if (title.isNotEmpty() && body.isNotEmpty()){
//                val postModel = PostModel()
//                postModel.userId = 1
//                postModel.title = title
//                postModel.body = body
//
//                vm.createPost(postModel)
//
//                vm.createPostLiveData?.observe(this, Observer {
//                    if (it!=null){
//                        adapter.addData(postModel)
//                        rv_home.smoothScrollToPosition(0)
//                    }else{
//                        showToast("Cannot create post at the moment")
//                    }
//                    dialog.cancel()
//                })
//
//            }else{
//                showToast("Please fill data carefully!")
//            }
//
//        }
//
//        dialog.show()
//
//        val window = dialog.window
//        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
//
//    }

    private fun initAdapter() {
        adapter = HomeAdapter(this)
        rv_home.layoutManager = LinearLayoutManager(this)
        rv_home.adapter = adapter
    }

    override fun onItemDeleted(postModel: PostModel, position: Int) {

    }

    override fun onItemClicked(item: PostModel, itemView: View) {

        val myIntent = Intent(this, SchoolInfoActivity::class.java)
        myIntent.putExtra("dbn", item.dbn) //Optional parameters
        myIntent.putExtra("school_name", item.school_name) //Optional parameters

        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,  // Now we provide a list of Pair items which contain the view we can transitioning
            // from, and the name of the view it is transitioning to, in the launched activity
            Pair<View, String>( itemView.findViewById<View>(R.id.tv_home_item_title),  SchoolInfoActivity.VIEW_NAME_SCHOOL_NAME   ),

            )
        // Now we can start the Activity, providing the activity options as a bundle
        ActivityCompat.startActivity(this, myIntent, activityOptions.toBundle())

    }

    private fun showToast(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

}
