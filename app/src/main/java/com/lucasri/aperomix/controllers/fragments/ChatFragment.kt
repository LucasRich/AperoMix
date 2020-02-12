package com.lucasri.aperomix.controllers.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.lucasri.aperomix.R
import com.lucasri.aperomix.controllers.activities.AccountActivity
import com.lucasri.aperomix.controllers.activities.MainActivity
import com.lucasri.aperomix.database.injection.ChatViewModelFactory
import com.lucasri.aperomix.database.injection.UserViewModelFactory
import com.lucasri.aperomix.database.repository.ChatDataRepository
import com.lucasri.aperomix.database.repository.UserDataRepository
import com.lucasri.aperomix.models.Message
import com.lucasri.aperomix.models.User
import com.lucasri.aperomix.utils.getCurrentDate
import com.lucasri.aperomix.utils.launchActivity
import com.lucasri.aperomix.view.ChatViewModel
import com.lucasri.aperomix.view.UserViewModel
import com.lucasri.aperomix.view.adapter.ChatAdapter
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_chat.*
import java.util.concurrent.Executors

class ChatFragment : Fragment(), ChatAdapter.Listener{

    private lateinit var userViewModel: UserViewModel
    private lateinit var chatViewModel: ChatViewModel
    private var chatAdapter: ChatAdapter? = null

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        this.configureUserViewModel()
        this.configureChatViewModel()
        this.configureRecyclerView()

        if (MainActivity.dynamicLinkMode) dynamicLinkMode()

        fragment_chat_sendBtn.setOnClickListener {
            if (fragment_chat_msgEdt.text.toString() != ""){
                userViewModel.getUser(auth.uid!!).addOnSuccessListener { documentSnapshot ->
                    val currentUser = documentSnapshot.toObject(User::class.java)

                    initView(currentUser)
                    chatViewModel.createChatInFirestore(Message(fragment_chat_msgEdt.text.toString(), currentUser!!))
                    fragment_chat_msgEdt.setText("")
                }
            }
        }

        fragment_chat_upBtn.setOnClickListener { activity!!.launchActivity(MainActivity()) }
    }

    // -------------------
    // CONFIGURATION
    // -------------------

    private fun configureUserViewModel() {
        val userViewModelFactory = UserViewModelFactory(UserDataRepository(), Executors.newSingleThreadExecutor())
        this.userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel::class.java)
    }

    private fun configureChatViewModel() {
        val chatViewModelFactory = ChatViewModelFactory(ChatDataRepository(), Executors.newSingleThreadExecutor())
        this.chatViewModel = ViewModelProviders.of(this, chatViewModelFactory).get(ChatViewModel::class.java)
    }

    private fun configureRecyclerView() {
        this.chatAdapter = ChatAdapter(generateOptionsForAdapter(chatViewModel.getAllMessages()), this, auth.currentUser!!.uid)
        chatAdapter!!.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                fragment_chat_recyclerView.smoothScrollToPosition(chatAdapter!!.itemCount) // Scroll to bottom on new messages
            }
        })
        fragment_chat_recyclerView.layoutManager = LinearLayoutManager(context)
        fragment_chat_recyclerView.adapter = this.chatAdapter
    }

    // 6 - Create options for RecyclerView from a Query
    private fun generateOptionsForAdapter(query: Query): FirestoreRecyclerOptions<Message> {
        return FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message::class.java)
                .setLifecycleOwner(this)
                .build()
    }

    override fun onClickDeleteButton(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // ---------------------
    // UTILS
    // ---------------------

    private fun dynamicLinkMode(){
        MainActivity.dynamicLinkMode = false
        AlertDialog.Builder(context!!)
                .setMessage(getString(R.string.DynamicLink_message_chat))
                .show()
    }

    private fun initView(user: User?){

    }
}