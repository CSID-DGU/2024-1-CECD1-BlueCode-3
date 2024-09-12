import remarkGfm from 'remark-gfm';
import React, { useCallback } from 'react';
import ReactMarkdown from 'react-markdown';

const Markdown = React.memo(({ linkStopPropagation, ...props }) => {
    const handleLinkClick = useCallback(event => {
        event.StopPropagation();
    }, []);
    
    const linkRenderer = useCallback(
        ({ node, ...linkProps }) => <a {...linkProps} onClick={handleLinkClick} />,
        [handleLinkClick]
    );

    let renderers;
    if (linkStopPropagation) {
        renderers = {
            link : linkRenderer
        };
    }

    return <ReactMarkdown {...props} remarkPlugins={[remarkGfm]} components={renderers} />;
});

export default Markdown;