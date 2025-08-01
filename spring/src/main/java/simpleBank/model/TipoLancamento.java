package simpleBank.model;

public enum TipoLancamento {
    CREDITO, DEBITO;

    public static boolean contains(String s) {
        for (TipoLancamento tipos : values())
            if (tipos.name().equals(s))
                return true;
        return false;
    }
}