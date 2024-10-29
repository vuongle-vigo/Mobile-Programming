package com.example.gmail.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gmail.R

class EmailAdapter(private val emails: List<Email>) : RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_email, parent, false)
        return EmailViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        val email = emails[position]
        holder.tvIcon.text = email.senderInitial
        holder.tvSender.text = email.sender
        holder.tvSubject.text = email.subject
        holder.tvSnippet.text = email.snippet
        holder.tvTime.text = email.time
    }

    override fun getItemCount(): Int = emails.size

    class EmailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvIcon: TextView = view.findViewById(R.id.tvIcon)
        val tvSender: TextView = view.findViewById(R.id.tvSender)
        val tvSubject: TextView = view.findViewById(R.id.tvSubject)
        val tvSnippet: TextView = view.findViewById(R.id.tvSnippet)
        val tvTime: TextView = view.findViewById(R.id.tvTime)
    }
}

data class Email(
    val sender: String,
    val senderInitial: String,
    val subject: String,
    val snippet: String,
    val time: String
)
