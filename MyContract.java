package com.example.izi.memories;

import android.provider.BaseColumns;

public class MyContract implements BaseColumns {
    static class Memories{
        public static final String TABLE_NAME = "MEMORIES";

        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_TOTAL_DAY = "TotalDay";
        public static final String COLUMN_MEMORY = "Memory";

        public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ( "+
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+
                COLUMN_TOTAL_DAY + " INTEGER , "+
                COLUMN_MEMORY + " TEXT "+
                ")";
    }
}
