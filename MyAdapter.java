package com.example.izi.memories;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.example.izi.memories.MyContract.Memories.COLUMN_TOTAL_DAY;
import static com.example.izi.memories.MyContract.Memories.TABLE_NAME;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    SQLiteOpenHelper mSQL;
    SQLiteDatabase mDB;
    Cursor mCursor;

    public MyAdapter(Context context){
        mSQL = new MySQLiteOpenHelper(context, "", null, 1);;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ConstraintLayout layout = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.main_row, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(layout);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Log.i("XXXXX", "ON BIND");
        mCursor.moveToPosition(position);
        TextView memory = holder.itemView.findViewById(R.id.memory);
        memory.setText(mCursor.getString(2));
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void updateCursor(int totalDay){
        if(mCursor != null)
            mCursor.close();

        mDB = mSQL.getReadableDatabase();
        mCursor = mDB.query(TABLE_NAME, new String[]{"*"}, COLUMN_TOTAL_DAY+"=?", new String[]{String.valueOf(totalDay)}, null, null, null);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(ConstraintLayout itemView) {
            super(itemView);
        }
    }
}
