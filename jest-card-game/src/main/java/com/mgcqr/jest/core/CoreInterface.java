package com.mgcqr.jest.core;

import com.mgcqr.jest.dto.ws.GameInstructionDto;

public class CoreInterface {

    private GameInstructionDto instruction;
    private boolean full = false;

    public synchronized void produce(GameInstructionDto instruction){
        if(full) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.instruction = instruction;
        full = true;
        this.notify();
    }

    public synchronized GameInstructionDto consume(){
        GameInstructionDto res;
        if(!full) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        full = false;
        res = instruction;
        this.notify();
        return res;
    }
}
