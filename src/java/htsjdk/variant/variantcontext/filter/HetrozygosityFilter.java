package htsjdk.variant.variantcontext.filter;

import htsjdk.variant.variantcontext.Genotype;
import htsjdk.variant.variantcontext.VariantContext;

/**
 * A Predicate on VariantContexts that either returns true at hetrozygous sites (invertible to false).
 * if optional "sample" argument to constructor is given, the genotype of that sample will be examined,
 * otherwise first genotype will be used.
 *
 * Missing sample, or no genotype will result in an exception being thrown.
 */
public class HetrozygosityFilter implements VariantContextFilter {

    private String sample;
    private boolean keepHets;

    /**
     * Constructor
     *
     * @param keepHets determine whether to keep the het sites ( true ) or filter them out ( false )
     * @param sample the name of the sample in the variant context whose genotype should be examined.
     */

    public HetrozygosityFilter(boolean keepHets, String sample) {
        this.keepHets = keepHets;
        this.sample = sample;
    }

    public HetrozygosityFilter(boolean keepHets) {
        this(keepHets, null);
    }

    /* @return true if the VariantContext not be kept, otherwise false
    * Assumes that <sample> is a sample in the vcf. */
    @Override
    public boolean filterOut(final VariantContext record) {

        final Genotype gt;
        if (sample == null) {
            gt = record.getGenotype(0);
            if (gt == null) {
                throw new UnsupportedOperationException("Cannot find any genotypes in VariantContext: "+ record);
            }
        } else {
            gt = record.getGenotype(sample);
            if (gt == null) {
                throw new UnsupportedOperationException("Cannot find sample requested: "+ sample);
            }
        }

        //XOR operator to reverse behaviour if keepHets is true.
        return gt.isHet() ^ keepHets;
    }
}
