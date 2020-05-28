package com.example.tic_tac_toe;

public class player implements Comparable<player> {
    String name;
    Integer score;

    public player(String name, Integer score) {
        this.name = name;
        this.score = score;
    }

    public void addScore(Integer i){
        score=score+i;
    }

    public String getName() {
        return name;
    }

    public Integer getScore() {
        return score;
    }

    @Override
    public int compareTo(player player) {
        return (this.getScore() > player.getScore() ? -1 :
                (this.getScore() == player.getScore() ? 0 : 1));
    }
}
