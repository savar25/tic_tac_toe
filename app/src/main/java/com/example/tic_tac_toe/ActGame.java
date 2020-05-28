package com.example.tic_tac_toe;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.logging.Handler;

public class ActGame extends View {

    public ArrayList<ArrayList<Point>> grid=new ArrayList<>();
    private static final String TAG = "ActGame";
    public ArrayList<ArrayList<Integer>> vals=new ArrayList<>();
    public ArrayList<Point> marked=new ArrayList<>();
    public Bitmap bitmap;
    public Canvas canvas;
    Paint paint,bpaint,winPaint;
    int row,col;
    int turn=1;
    Drawable d;
    public Integer val1,val2;
    public boolean finalFlag=false;
    public int players=2;

    boolean spFlag=false;
    public class Move{
        int row,col;
    }
    int comp=2,opp=1;

    public void setPlayers(int players) {
        this.players = players;
    }

    public ActGame(Context context, AttributeSet attrs) {
        super(context, attrs);

        setupPaint();
        TypedArray a=context.getTheme().obtainStyledAttributes(attrs,R.styleable.ActGame,0,0);

        try {
            row=a.getInteger(R.styleable.ActGame_rows,4);
            col=a.getInteger(R.styleable.ActGame_columns,4);
        }finally {
            a.recycle();
        }

        for(int i=0;i<3;i++){
            ArrayList<Integer> temp=new ArrayList<>();
            for(int j=0;j<3;j++){
                temp.add(0);
            }
            vals.add(temp);
        }


    }

    public void setupPaint(){
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.rBlue));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeCap(Paint.Cap.BUTT);

        bpaint = new Paint();
        bpaint.setColor(getResources().getColor(R.color.borderGreen));
        bpaint.setAntiAlias(false);
        bpaint.setStrokeWidth(40);
        bpaint.setStyle(Paint.Style.STROKE);
        bpaint.setStrokeJoin(Paint.Join.BEVEL);
        bpaint.setStrokeCap(Paint.Cap.ROUND);

        winPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        winPaint.setColor(Color.BLUE);
        winPaint.setAntiAlias(false);
        winPaint.setStrokeWidth(23);
        winPaint.setStyle(Paint.Style.STROKE);
        winPaint.setStrokeJoin(Paint.Join.MITER);
        winPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width=getMeasuredWidth();
        int height=getMeasuredHeight();


        int dwidth=getMeasuredWidth()/5;
        int dheight=getMeasuredHeight()/5;

        for(int i=0;i<4;i++){
            ArrayList<Point> temp=new ArrayList<>();
            for(int j=0;j<4;j++){
                temp.add(new Point(dwidth+j*dwidth,dheight+i*dheight));
            }
            grid.add(temp);

        }

        //Bitmap Setup
        canvas.drawBitmap(bitmap, 0, 0, paint);


        canvas.drawLine(grid.get(0).get(1).x,grid.get(0).get(1).y,grid.get(3).get(1).x,grid.get(3).get(1).y,paint);
        canvas.drawLine(grid.get(0).get(2).x,grid.get(0).get(2).y,grid.get(3).get(2).x,grid.get(3).get(2).y,paint);
        canvas.drawLine(grid.get(1).get(0).x,grid.get(1).get(0).y,grid.get(1).get(3).x,grid.get(1).get(3).y,paint);
        canvas.drawLine(grid.get(2).get(0).x,grid.get(2).get(0).y,grid.get(2).get(3).x,grid.get(2).get(3).y,paint);

        canvas.drawRect(0,0,width,height,bpaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int dwidth=getMeasuredWidth()/5;
        int dheight=getMeasuredHeight()/5;




        Point touchPoint=new Point((int)event.getX(),(int)event.getY());

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(players==2){
                    Point refPoint = TLPoint(touchPoint);
                    if (!(checkmark(refPoint)) && checkRef(refPoint)) {
                        if (Build.VERSION.SDK_INT > 21) {
                            if (turn == 1) {
                                d = getResources().getDrawable(R.drawable.ucross, null);
                                ArrayList<Integer> t = vals.get(val1);
                                t.set(val2, turn);
                                vals.set(val1, t);
                                turn = 2;
                                marked.add(refPoint);

                        } else if (turn == 2) {
                            d = getResources().getDrawable(R.drawable.ucircle, null);
                            ArrayList<Integer> t = vals.get(val1);
                            t.set(val2, turn);
                            vals.set(val1, t);
                            turn = 1;
                            marked.add(refPoint);

                        }
                            d.setBounds(refPoint.x + 20, refPoint.y + 20, refPoint.x + dwidth - 20, refPoint.y + dheight - 20);
                            d.draw(canvas);
                            checkWin();

                    }
                        if(finalFlag==false) {
                            gamePage.setPLayer(turn);
                        }
                    }

                }else if(players==1) {
                    Point refPoint = TLPoint(touchPoint);
                    if (!(checkmark(refPoint)) && checkRef(refPoint)) {
                        if (Build.VERSION.SDK_INT > 21) {
                            if (turn == 1) {
                                d = getResources().getDrawable(R.drawable.ucross, null);
                                ArrayList<Integer> t = vals.get(val1);
                                t.set(val2, turn);
                                vals.set(val1, t);
                                marked.add(refPoint);
                                turn=2;
                            }

                            d.setBounds(refPoint.x + 20, refPoint.y + 20, refPoint.x + dwidth - 20, refPoint.y + dheight - 20);
                            d.draw(canvas);
                            checkWin();
                        }



                        if(Build.VERSION.SDK_INT>=21) {
                            if(turn==2 && !spFlag){
                            Move move = findBestMove();
                            refPoint=grid.get(move.row).get(move.col);
                            if (!(checkmark(refPoint)) && checkRef(refPoint)) {
                                d = getResources().getDrawable(R.drawable.ucircle, null);
                                ArrayList<Integer> t = vals.get(move.row);
                                t.set(move.col, 2);
                                vals.set(move.row, t);
                                marked.add(grid.get(move.row).get(move.col));
                                turn=1;
                                d.setBounds(refPoint.x + 20, refPoint.y + 20, refPoint.x + dwidth - 20, refPoint.y + dheight - 20);
                                d.draw(canvas);
                                checkWin();
                            }

                            }
                        }
                        if(!finalFlag){
                            gamePage.setPLayer(1);
                        }
                    }
                }
        }



        Log.d(TAG, "onTouchEvent: "+vals);

        invalidate();
        return true;
    }

    public Point TLPoint(Point p){

        int TLx=0,TLy=0;
        for(int i=0;i<3;i++){
            if(p.y>grid.get(i).get(0).y &&p.y<=grid.get(i+1).get(0).y){
                TLy=grid.get(i).get(0).y;
                val1=i;
            }
            if(p.x>grid.get(0).get(i).x && p.x<=grid.get(0).get(i+1).x){
                TLx=grid.get(0).get(i).x;
                val2=i;
            }
        }

        return new Point(TLx,TLy);
    }

    public boolean checkRef(Point point){
        for(ArrayList<Point> row:grid){
            for(Point p:row){
                if(p.x==point.x && p.y==point.y){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean checkmark(Point point){
        Log.d(TAG, "checkmark: "+ point);
        Log.d(TAG, "checkmark: "+ marked);
        for(int i=0;i<marked.size();i++){
            if(point.x==marked.get(i).x && point.y==marked.get(i).y){
                return true;
            }
        }

        return false;
    }

    public void checkWin(){
        boolean flaf=true;
        for(int i=0;i<3;i++){
            if(vals.get(i).get(0)==vals.get(i).get(1) && vals.get(i).get(2)==vals.get(i).get(1) && vals.get(i).get(0)==vals.get(i).get(2) && (checkTriplenull(vals.get(i).get(0),vals.get(i).get(1),vals.get(i).get(2)))){
                canvas.drawLine((grid.get(i).get(0).x+grid.get(i).get(1).x)/2,(grid.get(i).get(0).y+grid.get(i+1).get(0).y)/2,(grid.get(i).get(2).x+grid.get(i).get(3).x)/2,(grid.get(i).get(0).y+grid.get(i+1).get(0).y)/2,winPaint);
                finalFlag=true;
                flaf=false;
                if(players==1){
                    spFlag=true;
                }
                gamePage.declareWinner(turn);

            }

            if(vals.get(0).get(i)==vals.get(1).get(i) && vals.get(2).get(i)==vals.get(1).get(i) && vals.get(0).get(i)==vals.get(2).get(i)  && (checkTriplenull(vals.get(0).get(i),vals.get(1).get(i),vals.get(2).get(i)))){
              canvas.drawLine((grid.get(0).get(i).x+grid.get(0).get(i+1).x)/2,(grid.get(0).get(i).y+grid.get(1).get(i).y)/2,(grid.get(0).get(i).x+grid.get(0).get(i+1).x)/2,(grid.get(2).get(i).y+grid.get(3).get(i).y)/2,winPaint);
              finalFlag=true;
              flaf=false;
                if(players==1){
                    spFlag=true;
                }
              gamePage.declareWinner(turn);
            }

        }

        if(vals.get(0).get(0)==vals.get(1).get(1) && vals.get(0).get(0)==vals.get(2).get(2) && vals.get(2).get(2)==vals.get(1).get(1) && (checkTriplenull(vals.get(0).get(0),vals.get(1).get(1),vals.get(2).get(2)))){
            canvas.drawLine((grid.get(0).get(0).x+grid.get(1).get(1).x)/2,(grid.get(0).get(0).y+grid.get(1).get(1).y)/2,(grid.get(2).get(2).x+grid.get(3).get(3).x)/2,(grid.get(2).get(2).y+grid.get(3).get(3).y)/2,winPaint);
            finalFlag=true;
            flaf=false;
            if(players==1){
                spFlag=true;
            }
            gamePage.declareWinner(turn);
        }

        if(vals.get(0).get(2)==vals.get(1).get(1) && vals.get(0).get(2)==vals.get(2).get(0) && vals.get(2).get(0)==vals.get(1).get(1) && (checkTriplenull(vals.get(2).get(0),vals.get(1).get(1),vals.get(0).get(2)))){
            canvas.drawLine((grid.get(0).get(3).x+grid.get(1).get(2).x)/2,(grid.get(0).get(3).y+grid.get(1).get(2).y)/2,(grid.get(2).get(1).x+grid.get(3).get(0).x)/2,(grid.get(2).get(1).y+grid.get(3).get(0).y)/2,winPaint);
            finalFlag=true;
            flaf=false;
            if(players==1){
                spFlag=true;
            }
            gamePage.declareWinner(turn);
        }

        if(marked.size()==9 && flaf){
            Log.d(TAG, "checkWin: max");
            finalFlag=true;
            if(players==1){
                spFlag=true;
            }
            gamePage.declareWinner(3);
        }


    }

    public boolean checkTriplenull(int a,int b,int c){
        if(a==0 && b==0 && c==0){
            return false;
        }
        return true;
    }





    public Boolean isMovesLeft()
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (vals.get(i).get(j)==0)
                    return true;
        return false;
    }

    public int evaluate()
    {
        // Checking for Rows for X or O victory. 
        for (int row = 0; row < 3; row++)
        {
            if (vals.get(row).get(0) == vals.get(row).get(1) &&
                    vals.get(row).get(1) == vals.get(row).get(2) && checkTriplenull(vals.get(row).get(0),vals.get(row).get(1),vals.get(row).get(2)))
            {
                if (vals.get(row).get(0) == comp)
                    return +10;
                else if (vals.get(row).get(0) == opp)
                    return -10;
            }
        }

        // Checking for Columns for X or O victory. 
        for (int col = 0; col < 3; col++)
        {
            if (vals.get(0).get(col) == vals.get(1).get(col) &&
                    vals.get(1).get(col) == vals.get(2).get(col) && checkTriplenull(vals.get(0).get(col),vals.get(1).get(col),vals.get(2).get(col)))
            {
                if (vals.get(0).get(col) == comp)
                    return +10;

                else if (vals.get(0).get(col) == opp)
                    return -10;
            }
        }

        // Checking for Diagonals for X or O victory. 
        if (vals.get(0).get(0) == vals.get(1).get(1) && vals.get(1).get(1) == vals.get(2).get(2) && checkTriplenull(vals.get(0).get(0),vals.get(1).get(1),vals.get(2).get(2)))
        {
            if (vals.get(0).get(0) == comp)
                return +10;
            else if (vals.get(0).get(0) == opp)
                return -10;
        }

        if (vals.get(0).get(2) == vals.get(2).get(2) && vals.get(2).get(2) == vals.get(2).get(0) && checkTriplenull(vals.get(1).get(0),vals.get(1).get(1),vals.get(0).get(2)))
        {
            if (vals.get(0).get(2) == comp)
                return +10;
            else if (vals.get(0).get(2) == opp)
                return -10;
        }

        // Else if none of them have won then return 0 
        return 0;
    }


    public int minimax(int depth, Boolean isMax) {
        int score = evaluate();

        // If Maximizer has won the game
        // return his/her evaluated score
        if (score == 10)
            return score;

        // If Minimizer has won the game
        // return his/her evaluated score
        if (score == -10)
            return score;

        // If there are no more moves and
        // no winner then it is a tie
        if (isMovesLeft() == false)
            return 0;

        // If this maximizer's move
        if (isMax) {
            int best = -1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (vals.get(i).get(j) == 0) {
                        // Make the move
                        ArrayList<Integer> temp = vals.get(i);
                        temp.set(j, comp);
                        vals.set(i, temp);

                        // Call minimax recursively and choose
                        // the maximum value
                        best = Math.max(best, minimax(
                                depth + 1, !isMax));

                        // Undo the move
                        ArrayList<Integer> temp1 = vals.get(i);
                        temp1.set(j, 0);
                        vals.set(i, temp1);

                    }
                }
            }
            return best;
        }

        // If this minimizer's move
        else {
            int best = 1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (vals.get(i).get(j) == 0) {
                        // Make the move
                        ArrayList<Integer> temp = vals.get(i);
                        temp.set(j, opp);
                        vals.set(i, temp);


                        // Call minimax recursively and choose
                        // the minimum value
                        best = Math.min(best, minimax(
                                depth + 1, !isMax));

                        // Undo the move
                        ArrayList<Integer> temp1 = vals.get(i);
                        temp1.set(j, 0);
                        vals.set(i, temp1);

                    }
                }
            }
            return best;
        }
    }

    public Move findBestMove()
    {
        int bestVal = -1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        // Traverse all cells, evaluate minimax function  
        // for all empty cells. And return the cell  
        // with optimal value. 
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                // Check if cell is empty 
                if (vals.get(i).get(j) == 0)
                {
                    // Make the move 
                    ArrayList<Integer> temp = vals.get(i);
                    temp.set(j, comp);
                    vals.set(i, temp);

                    // compute evaluation function for this 
                    // move. 
                    int moveVal = minimax(0, false);

                    // Undo the move 
                    ArrayList<Integer> temp1 = vals.get(i);
                    temp1.set(j, 0);
                    vals.set(i, temp1);

                    // If the value of the current move is 
                    // more than the best value, then update 
                    // best/ 
                    if (moveVal > bestVal)
                    {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }


        return bestMove;
    }
}
