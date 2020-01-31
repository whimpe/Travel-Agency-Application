/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java.sql;


import java.util.Comparator;

/**
 *
 * @author Willem Himpe
 */
public class Node implements Comparator<Node> { 
    public String node; 
    public int cost; 
  
    public Node() 
    { 
    } 
  
    public Node(String node, int cost) 
    { 
        this.node = node; 
        this.cost = cost; 
    } 
  
    @Override
    public int compare(Node node1, Node node2) 
    { 
        if (node1.cost < node2.cost) 
            return -1; 
        if (node1.cost > node2.cost) 
            return 1; 
        return 0; 
    } 
} 