package com.example.snake;

public interface Subject {
    void registerObserver(OnTouch observer);
    void removeObserver(OnTouch Observer);
    void notifyObservers();
}
