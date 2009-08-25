/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.email;

import com.android.email.mail.Message;
import com.android.email.provider.EmailContent;

import android.content.Context;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class GroupMessagingListener extends MessagingListener {
    /* The synchronization of the methods in this class
       is not needed because we use ConcurrentHashMap.
       
       Nevertheless, let's keep the "synchronized" for a while in the case
       we may want to change the implementation to use something else
       than ConcurrentHashMap.
    */

    private ConcurrentHashMap<MessagingListener, Object> mListenersMap =
        new ConcurrentHashMap<MessagingListener, Object>();

    private Set<MessagingListener> mListeners = mListenersMap.keySet();

    synchronized public void addListener(MessagingListener listener) {
        // we use "this" as a dummy non-null value
        mListenersMap.put(listener, this);
    }

    synchronized public void removeListener(MessagingListener listener) {
        mListenersMap.remove(listener);
    }

    synchronized public boolean isActiveListener(MessagingListener listener) {
        return mListenersMap.containsKey(listener);
    }

    @Override
    synchronized public void listFoldersStarted(EmailContent.Account account) {
        for (MessagingListener l : mListeners) {
            l.listFoldersStarted(account);
        }
    }

    @Override
    synchronized public void listFoldersFailed(EmailContent.Account account, String message) {
        for (MessagingListener l : mListeners) {
            l.listFoldersFailed(account, message);
        }
    }

    @Override
    synchronized public void listFoldersFinished(EmailContent.Account account) {
        for (MessagingListener l : mListeners) {
            l.listFoldersFinished(account);
        }
    }

    @Override
    synchronized public void synchronizeMailboxStarted(EmailContent.Account account, 
                                                       EmailContent.Mailbox folder) {
        for (MessagingListener l : mListeners) {
            l.synchronizeMailboxStarted(account, folder);
        }
    }

    @Override
    synchronized public void synchronizeMailboxFinished(EmailContent.Account account,
            EmailContent.Mailbox folder, int totalMessagesInMailbox, int numNewMessages) {
        for (MessagingListener l : mListeners) {
            l.synchronizeMailboxFinished(account, folder, totalMessagesInMailbox, numNewMessages);
        }
    }

    @Override
    synchronized public void synchronizeMailboxFailed(EmailContent.Account account, 
                                                      EmailContent.Mailbox folder,
                                                      Exception e) {
        for (MessagingListener l : mListeners) {
            l.synchronizeMailboxFailed(account, folder, e);
        }
    }

    @Override
    synchronized public void loadMessageForViewStarted(long messageId) {
        for (MessagingListener l : mListeners) {
            l.loadMessageForViewStarted(messageId);
        }
    }

    @Override
    synchronized public void loadMessageForViewFinished(long messageId) {
        for (MessagingListener l : mListeners) {
            l.loadMessageForViewFinished(messageId);
        }
    }

    @Override
    synchronized public void loadMessageForViewFailed(long messageId, String message) {
        for (MessagingListener l : mListeners) {
            l.loadMessageForViewFailed(messageId, message);
        }
    }

    @Override
    synchronized public void checkMailStarted(Context context, long accountId, long tag) {
        for (MessagingListener l : mListeners) {
            l.checkMailStarted(context, accountId, tag);
        }
    }

    @Override
    synchronized public void checkMailFinished(Context context, long accountId, long folderId,
            long tag) {
        for (MessagingListener l : mListeners) {
            l.checkMailFinished(context, accountId, folderId, tag);
        }
    }

    @Override
    synchronized public void sendPendingMessagesStarted(long accountId, long messageId) {
        for (MessagingListener l : mListeners) {
            l.sendPendingMessagesStarted(accountId, messageId);
        }
    }

    @Override
    synchronized public void sendPendingMessagesCompleted(long accountId) {
        for (MessagingListener l : mListeners) {
            l.sendPendingMessagesCompleted(accountId);
        }
    }

    @Override
    synchronized public void sendPendingMessagesFailed(long accountId, long messageId,
            Exception reason) {
        for (MessagingListener l : mListeners) {
            l.sendPendingMessagesFailed(accountId, messageId, reason);
        }
    }

    @Override
    synchronized public void emptyTrashCompleted(EmailContent.Account account) {
        for (MessagingListener l : mListeners) {
            l.emptyTrashCompleted(account);
        }
    }

    @Override
    synchronized public void messageUidChanged(EmailContent.Account account, String folder,
            String oldUid, String newUid) {
        for (MessagingListener l : mListeners) {
            l.messageUidChanged(account, folder, oldUid, newUid);
        }
    }

    @Override
    synchronized public void loadAttachmentStarted(
            long accountId,
            long messageId,
            long attachmentId,
            boolean requiresDownload) {
        for (MessagingListener l : mListeners) {
            l.loadAttachmentStarted(accountId, messageId, attachmentId, requiresDownload);
        }
    }

    @Override
    synchronized public void loadAttachmentFinished(
            long accountId,
            long messageId,
            long attachmentId) {
        for (MessagingListener l : mListeners) {
            l.loadAttachmentFinished(accountId, messageId, attachmentId);
        }
    }

    @Override
    synchronized public void loadAttachmentFailed(
            long accountId,
            long messageId,
            long attachmentId,
            String reason) {
        for (MessagingListener l : mListeners) {
            l.loadAttachmentFailed(accountId, messageId, attachmentId, reason);
        }
    }

    @Override
    synchronized public void controllerCommandCompleted(boolean moreCommandsToRun) {
        for (MessagingListener l : mListeners) {
            l.controllerCommandCompleted(moreCommandsToRun);
        }
    }
}
