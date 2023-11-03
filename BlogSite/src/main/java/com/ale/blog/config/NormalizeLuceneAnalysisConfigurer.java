package com.ale.blog.config;

import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;

public class NormalizeLuceneAnalysisConfigurer implements LuceneAnalysisConfigurer {
    @Override
    public void configure(LuceneAnalysisConfigurationContext luceneAnalysisConfigurationContext) {
        luceneAnalysisConfigurationContext.analyzer( "vietnamese" ).custom()
                .tokenizer( "standard" )
                .charFilter( "htmlStrip" )
                .tokenFilter( "lowercase" )
//                .tokenFilter( "snowballPorter" )
//                .param( "language", "English" )
                .tokenFilter( "asciiFolding" );
    }
}
