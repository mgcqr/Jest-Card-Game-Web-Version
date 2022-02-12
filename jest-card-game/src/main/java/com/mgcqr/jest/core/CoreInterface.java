package com.mgcqr.jest.core;

import com.mgcqr.jest.model.InstructionInfo;

public class CoreInterface {

    private InstructionInfo instruction;
    private boolean full = false;

    public synchronized void produce(InstructionInfo inst){
        if(full) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        instruction = inst;
        full = true;
        this.notify();
    }

    public synchronized InstructionInfo consume(){
        InstructionInfo res;
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
