package fr.usmb.m1isc.compilation.tp;

import java.io.FileWriter;
import java.io.IOException;

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
            String res= "DATA SEGMENT";
            res+= dataSegment();
            res+="\nDATA ENDS";

            res+="\nCODE SEGMENT";
            res+=codeSegment();
            res+="\nCODE ENDS";
            System.out.println(res);

            try {
                FileWriter fw = new FileWriter("abcd.asm");
                fw.write(res);
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static int id_niveau=0;

        public String codeSegment(){
            System.out.println("VALUE HERE : "+this.value.toString());
            String str="";
            //return this.value.toString();
            switch(this.value.toString()) {
                case "let":
                    System.out.println(this.rightChild.codeSegment() +"\n mov eax, "+this.leftChild.value.toString()+"\n");
                    return this.rightChild.codeSegment() +"\n\tmov "+this.leftChild.value.toString()+", eax";
                    //return this.rightChild.codeSegment() +"\n mov"+this.leftChild.value.toString()+",eax";
                case "WHILE":
                    id_niveau++;
                    return("\ndebut_while_"+id_niveau+":"+this.leftChild.codeSegment()+"\n\tjz sortie_while_"+id_niveau+this.rightChild.codeSegment()
                            +"\n\tjmp debut_while_"+id_niveau+"\nsortie_while_"+id_niveau+":");
                case "IF":
                    id_niveau++;
                    return( this.leftChild.codeSegment()+"\n\tjz faux_if_"+id_niveau+this.rightChild.codeSegment()+"\n\tjmp sortie_if_"+id_niveau
                            +"\nfaux_if_"+id_niveau+" :"+ this.rightChild.codeSegment()+"\nsortie_if_"+id_niveau+" :");
                case "mod":
                    return (this.rightChild.codeSegment()+"\n\tpush eax"+this.leftChild.codeSegment()
                            +"\n\tpop ebx"+"\n\tmov ecx, eax"+"\n\tdiv ecx, ebx"+"\n\tmul ecx, ebx"+"\n\tsub eax, ecx");
                case "+":
                    // code block
                    return(this.calc() + "\n\tadd eax, ebx");
                case "-":
                    return(this.calc() + "\n\tsub ebx, eax"+"\n\tmov eax, ebx");
                case "/":
                    return(this.calc() + "\n\tdiv ebx, eax"+"\n\tmov eax, ebx");
                case "*":
                    return(this.calc()+ "\n\tmul eax, ebx");
                case ">=":
                    id_niveau++;
                    return(this.leftChild.codeSegment()+"\tpush eax\n"+this.rightChild.codeSegment()+"\n\tpop ebx"
                            +"\n\tsub eax, ebx"+"\n\tjl faux_gte_"+id_niveau+"\n\tmov eax, 1"+"\n\tjmp sortie_gte_"+id_niveau+"\nfaux_gte_"+id_niveau+" :"
                            +"\n\tmov eax, 0"+"\nsortie_gte_"+id_niveau+" :");
                case ">":
                    id_niveau++;
                  return (this.leftChild.codeSegment()+"\n\tpush eax"+this.rightChild.codeSegment()+"\n\tpop ebx"
                          +"\n\tsub eax, ebx"+"\n\tjle faux_gt_"+id_niveau+"\n\tmov eax, 1"+"\n\tjmp sortie_gt_"+id_niveau
                          +"\nfaux_gt_"+id_niveau+" :"+"\n\tmov eax, 0"+"\nsortie_gt_"+id_niveau+" :");
                case "IDENT":
                    return "\n\tmov eax, "+this.leftChild.value.toString();
                case "!":
                    return this.leftChild.codeSegment()+"\n\tnot eax"+"\n\tadd eax, 2";
                case "and":
                    id_niveau++;
                    return (this.leftChild.codeSegment()+"\tpush eax\n"+this.rightChild.codeSegment()+"\tpop ebx\n\tor eax, ebx\n\tjz faux_and_"+id_niveau+"\n\tmov eax, 1\n\tjmp sortie_and_"+id_niveau+"\nfaux_and_"+id_niveau+"\n\tmov eax, 0\nsortie_and_"+id_niveau+":\n");
                case "or":
                    id_niveau++;
                    return (this.leftChild.codeSegment()+"\tpush eax\n"+this.rightChild.codeSegment()+"\tpop ebx\n\tor eax, ebx\n\tjz faux_or_"+id_niveau+"\n\tmov eax, 1\n\tjmp sortie_or_"+id_niveau+"\nfaux_or_"+id_niveau+"\n\tmov eax, 0\nsortie_or_"+id_niveau+":\n");
                case "equal":
                    id_niveau++;
                    return( this.leftChild.codeSegment()+"\tpush eax\n"
                            +this.rightChild.codeSegment()+"\tpop ebx\n"+"\tsub eax, ebx\n"+"\tjnz faux_egal_"+id_niveau+"\n"
                            +"\tmov eax, 1\n"+"\tjmp sortie_egal_"+id_niveau+"\n"+"faux_egal_"+id_niveau+" :\n"
                            + "\tmov eax, 0\n"+"sortie_egal_"+id_niveau+" :\n");
                case "INPUT":
                    System.out.println();
                    return "\n\tin eax";
                case "OUTPUT":
                    return leftChild.codeSegment()+"\n\tout eax";
                case "MOINS_UNAIRE":
                    System.out.println("BANANNANNANANNANANNA");
                    return leftChild.codeSegment()+"\n\tmul eax, -1";
                case ";":
                    //return this.leftChild.toString();
                    str = leftChild.codeSegment();
                    if(this.rightChild!=null) str += rightChild.codeSegment();
                    return str;
                    //return leftChild.codeSegment()+ rightChild.codeSegment();
                default:
                    System.out.println("mov "+this.value.toString()+", eax\n");
                    //return "";
                    return "\n\tmov eax, "+ this.value;
                  // code block
              }

        }

        public String calc(){
            return(this.leftChild.codeSegment() + "\n\tpush eax \n"  + this.rightChild.codeSegment()+"\n\tpop ebx \n");
        }

        public String dataSegment(){
            //c recursif donc on peut pas mettre les DATA SEGMENTS ET DATA ENDS dans cette fonction
            String result ="";
            if(this.value=="let"){
                //donc la je veux verifier si on est arrive a un noeud pour declarer une variable 
                //et la rajouter au resultat si c le cas
                //sinon on dois continnuer dans fils gauche et droit
                result += "\n\t" + this.leftChild.value + " DD";
                
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
