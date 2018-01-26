println "Create file ..."
def index = 666;
def rulesf = new File('rules.txt')
rulesf.write "BEGIN"

for (index = 140; index < 20140; index++) {
    rulesf << """
rule "purchase is ${index} dollars"
when 
    \$p : Purchase (total == ${index}) 
then 
    \$p.setDiscount(\$p.getDiscount() + 0.03);
    System.out.println("is ${index} discount: " + \$p.getDiscount());
end
"""
}
rulesf << "END"

println "File created."
