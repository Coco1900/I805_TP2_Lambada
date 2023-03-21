package fr.usmb.m1isc.compilation.tp;

public class Noeud<T> {

        private final T value;
        private Noeud leftChild;
        private Noeud rightChild;


        public Noeud(T value, Noeud leftChild, Noeud rightChild){
            this.value = value;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }

    public Noeud(T value, Noeud leftChild){
        this.value = value;
        this.leftChild = leftChild;
        this.rightChild = null;
    }

        public Noeud(T value){
            this.value = value;

        }

        public boolean isAlone(){
            return (this.rightChild == null && this.leftChild == null);
        }


        @Override
        public String toString(){

            String str= "";
            str += this.isAlone()? "" : "(";
            str+=this.value+ " ";
            if(leftChild!=null)
                str+= leftChild.toString() + " ";
            if(rightChild!=null)
                str+= rightChild.toString();
            str += this.isAlone()? "" : ")";

            return str;
        }
}
