package ch.bfh.bti3001.snailsweeper.model;

public enum CellType {
    SolidVisible("SV"),
    EmptyHidden("EH"),
    EmptyVisible("EV"),
    EmptyFlagged("EF"),
    LivingSnailHidden("LH"),
    LivingSnailFlagged("LF"),
    CrushedSnailVisible("CV"),
	ShowDistanceToBomb("XX"),
	ShowDistanceToBomb1("XX"),
	ShowDistanceToBomb2("2 "),
	ShowDistanceToBomb3("3 "),
	ShowDistanceToBomb4("4 "),
	ShowDistanceToBomb5("5 ");

    public String label;

    private CellType(String label) {
        this.label = label;
    }
    
    public void setLabel(String string) {
    	this.label = string;
    }   
    
    public String getLabel() {
        return this.label;
    }
    
    public static CellType fromString(String label) {
        for (CellType ct : CellType.values()) {
            if (ct.label.equals(label)) {
                return ct;
            }
        }
        return null;
    }

    public static String toStringLabel(CellType ct) {
    	return ct.label;
    }
}