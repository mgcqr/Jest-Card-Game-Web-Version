package com.mgcqr.jest.core.role;

public interface Element {
    void accept(Visitor visitor);
}
//访问者模式
//定义一组可以被访问者访问的元素 这些元素实现Element接口  这里只有Joueur