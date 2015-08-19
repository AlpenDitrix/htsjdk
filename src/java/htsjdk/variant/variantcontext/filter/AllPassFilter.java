package htsjdk.variant.variantcontext.filter;

import htsjdk.variant.variantcontext.Genotype;
import htsjdk.variant.variantcontext.VariantContext;

/**
 * Created by farjoun on 6/25/15.
 */
public class AllPassFilter implements VariantContextFilter {

    /* @return false so that all VCs are kept. */
    @Override
    public boolean filterOut(final VariantContext record) {

        return false;
    }
}
