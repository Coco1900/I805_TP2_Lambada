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
            str += this.value+ " ";
            if(leftChild!=null)
                str+= leftChild.toString() + " ";
            if(rightChild!=null)
                str+= rightChild.toString();
            str += this.isAlone()? "" : ")";

            return str;
        }

        public void production(){
            String res= "DATA SEGMENT";
            res+= dataSegment();
            res+="\n DATA ENDS";

            res+="\n CODE SEGMENT";
            res+=codeSegment();
            res+="\n CODE ENDS";
            System.out.println(res);
        }

        public String codeSegment(){
            System.out.println("VALUE HERE /"+this.value.toString());
            switch(this.value.toString()) {
                case "let":
                    System.out.println("in eax\n mov"+this.leftChild.value.toString()+",eax");
                    return this.rightChild.codeSegment() +"in eax\n mov"+this.leftChild.value.toString()+",eax";
                case "IF":
                  // code block
                  break;
                case "MOD":
                  // code block
                  break;
                case "+":
                  // code block
                  break;
                 case "-":
                  // code block
                  break;
                 case "/":
                  // code block
                  break;
                case "*":
                  // code block
                  break;
                case ">=":
                  // code block
                  break;
                case ">":
                  // code block
                  break;
                case ";":
                  // code block
                  break;
                default:
                  // code block
              }
              
            return "";
        }

        public String dataSegment(){
            //c recursif donc on peut pas mettre les DATA SEGMENTS ET DATA ENDS dans cette fonction
            String result ="";
            if(this.value=="let"){
                //donc la je veux verifier si on est arrive a un noeud pour declarer une variable 
                //et la rajouter au resultat si c le cas
                //sinon on dois continnuer dans fils gauche et droit
                result += "\n" + this.leftChild.value + " DD";
                
            }
            else{
                if(this.leftChild!=null){
                    result += this.leftChild.dataSegment();
                }
                if (this.rightChild!=null){
                    result +=this.rightChild.dataSegment();
                }
            }

            return result;
        }
}
