package com.example.brookiecooking.Adapter;

import static com.example.brookiecooking.MainActivity.allChats;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brookiecooking.Chat;
import com.example.brookiecooking.R;

import java.util.List;

public class MyChatViewAdapter extends RecyclerView.Adapter<MyChatViewAdapter.ViewHolder> {

    private List<Chat> chatList;

    public MyChatViewAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat, parent, false);

        return new ViewHolder(view);
    }





    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Chat currentChat = allChats.get(position);
        if (currentChat.isBot()){
            holder.botChatIcon.setVisibility(View.VISIBLE);
            holder.userChatIcon.setVisibility(View.GONE);
            holder.chatValue.setText(currentChat.getValue());
            /*RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.senderIconCardID.getLayoutParams();
            layoutParams.leftMargin = (int) (283 * holder.itemView.getContext().getResources().getDisplayMetrics().density);*/
            /*holder.senderIconCardID.setLayoutParams(layoutParams);*/
        }
        else{
            holder.botChatIcon.setVisibility(View.GONE);
            holder.userChatIcon.setVisibility(View.VISIBLE);
            holder.chatValue.setText(currentChat.getValue());
/*                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.charCardID.getLayoutParams();
                layoutParams.leftMargin = (int) (127 * holder.itemView.getContext().getResources().getDisplayMetrics().density);
                holder.charCardID.setLayoutParams(layoutParams);*/

//                FrameLayout.LayoutParams layoutParams1 = (FrameLayout.LayoutParams) holder.charCardID.getLayoutParams();
//                layoutParams1.gravity = Gravity.END;
//                holder.charCardID.setLayoutParams(layoutParams1);

        }


    }

    @Override
    public int getItemCount() {
        return allChats.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView chatValue;
        CardView charCardID;
        ImageView botChatIcon;
        ImageView userChatIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            chatValue = itemView.findViewById(R.id.chatValue);
            charCardID = itemView.findViewById(R.id.charCardID);
            userChatIcon = itemView.findViewById(R.id.userChatIcon);
            botChatIcon = itemView.findViewById(R.id.botChatIcon);


        }
    }
}

