package fr.usmb.m1isc.compilation.tp;

public class Noeud<T> {

        private final T value;
        private Noeud leftChild;
        private Noeud rightChild;


        public Noeud(T value, Noeud leftChild, Noeud rightChild) {
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
            String res= "DATA SEGMENT\n";
            res+= dataSegment();
            res+="\n DATA ENDS";

            res+="\n CODE SEGMENT\n";
            res+=codeSegment();
            res+="\n CODE ENDS";
            System.out.println(res);
        }

        public String codeSegment(){
            System.out.println("VALUE HERE : "+this.value.toString());
            String str="";
            //return this.value.toString();
            switch(this.value.toString()) {
                case "let":
                    System.out.println(this.rightChild.codeSegment() +"\n mov eax, "+this.leftChild.value.toString()+"\n");
                    return this.rightChild.codeSegment() +"\n mov eax, "+this.leftChild.value.toString()+"\n";
                    //return this.rightChild.codeSegment() +"\n mov"+this.leftChild.value.toString()+",eax";
                case "IF":
                  // code block
                    return "";
                case "mod":
                  // code block
                    return "";
                case "+":
                    // code block
                    return(this.calc() + "add eax, ebx\n" + "push eax\n");
                case "-":
                    return(this.calc() + "sub eax, ebx \n" + "push eax \n");
                case "/":
                    return(this.calc() + "mul eax, ebx\n" + "push eax\n");
                case "*":
                    return(this.calc()+ "div eax, ebx\n" +"push eax \n");
                case ">=":
                  // code block
                    return"";
                case ">":
                  // code block
                    return"";
                case "IDENT":
                    return "mov eax, "+this.leftChild.value.toString();
                case "!":
                    return"";
                case "and":
                    return"";
                case "or":
                    return"";
                case "equal":
                    return"";
                case "INPUT":
                    System.out.println();
                    return "in eax \n "+ this.leftChild.codeSegment();
                case "OUTPUT":
                    return"";
                case "MOINS_UNAIRE":
                    return"";
                case ";":
                    //return this.leftChild.toString();
                    str = leftChild.codeSegment();
                    if(this.rightChild!=null) str += rightChild.codeSegment();
                    return str;
                    //return leftChild.codeSegment()+ rightChild.codeSegment();
                default:
                    System.out.println("mov "+this.value.toString()+", eax\n");
                    //return "";
                    return "mov "+this.value.toString()+",eax\n";
                  // code block
              }

        }

        public String calc(){
            return(this.leftChild.codeSegment() + "push eax \n"  + this.rightChild.codeSegment()+"pop ebx \n");
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
